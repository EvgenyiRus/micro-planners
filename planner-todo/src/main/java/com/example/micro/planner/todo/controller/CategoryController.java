package com.example.micro.planner.todo.controller;

import com.example.micro.planner.entity.Category;
import com.example.micro.planner.entity.User;
import com.example.micro.planner.todo.feign.UserFeignClient;
import com.example.micro.planner.todo.search.CategorySearchValues;
import com.example.micro.planner.todo.service.CategoryService;
import com.example.micro.planner.utils.userBuilder.UserBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
    Используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON,
    иначе пришлось бы добавлять лишние объекты в код, использовать @ResponseBody для ответа, указывать тип отправки JSON
    Названия методов могут быть любыми, главное не дублировать их имена внутри класса и URL mapping
 **/
@RestController
@Slf4j
@RequestMapping("/category") // базовый URI
public class CategoryController {

    // доступ к данным из БД
    private final CategoryService categoryService;

    // микросервисы для работы с пользователями
    private final UserBuilder userBuilder;

    private final UserFeignClient userFeignClient;

    // используем автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public CategoryController(CategoryService categoryService, UserBuilder userWebClientBuilder,
                              UserFeignClient userFeignClient) {
        this.categoryService = categoryService;
        this.userBuilder = userWebClientBuilder;
        this.userFeignClient = userFeignClient;
    }

    @PostMapping("/all")
    public List<Category> findAll(@RequestBody Long userId) {
        return categoryService.findAll(userId);
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Category category) {

        // проверка на обязательные параметры
        if (category.getId() != null && category.getId() != 0) { // это означает, что id заполнено

            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }

        /* 1. проверка существования пользователя, вызовом мс из другого модуля
        метод вызывается синхронно, т.е. необходимо дождаться пока метод выполнится до конца
        С помощью RestTemplate
        */
//        if (userBuilder.userExists(category.getUserId())) {
//            return ResponseEntity.ok(categoryService.add(category));  // возвращаем добавленный объект
//        }

        /* 2. проверка существования пользователя, вызовом мс из другого модуля
        метод вызывается асинхронно, т.е. не дождавшись окончания работы метода будет вызван следующий метод (return new ... )
        subscribe - подписка на результат работы мс. Метод "userExistsAsync" выполняется паралл-о. Возвращается User
        для работы приложения здесь это не требуется
        */
        //userBuilder.userExistsAsync(category.getUserId()).subscribe((user->log.debug(String.valueOf(user)));

        // 3. вызов мс с помощью feign
        ResponseEntity<User> result = userFeignClient.findUserById(category.getUserId());
        if (result == null) { // если мс недоступен - вернется null
            return new ResponseEntity("система пользователей недоступна, попробуйте позже", HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (result.getBody() == null) {
            return new ResponseEntity(String.format("user id = %d not found", category.getUserId()), HttpStatus.ACCEPTED);
        }
        return ResponseEntity.ok(categoryService.add(category));
        // пользователь не найден
        // если пользователь не пустой
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category) {

        // проверка на обязательные параметры
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        categoryService.update(category);

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }


    // для удаления используем тип запроса DELETE и передаем ID для удаления
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            categoryService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 без объектов (операция прошла успешно)
    }

    // для удаления используем тип запроса DELETE и передаем ID для удаления
    // можно также использовать метод POST и передавать ID в теле запроса
    @PostMapping("/delete2")
    public ResponseEntity delete2(@RequestBody Long id) {

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            categoryService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 без объектов (операция прошла успешно)
    }

    // поиск по любым параметрам CategorySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues) {

        // проверка на обязательные параметры
        if (categorySearchValues.getUserId() == null || categorySearchValues.getUserId() <= 0) {
            return new ResponseEntity("missed param: user id", HttpStatus.NOT_ACCEPTABLE);
        }

        // поиск категорий пользователя по названию
        List<Category> list = categoryService.findByTitle(categorySearchValues.getTitle(), categorySearchValues.getUserId());

        return ResponseEntity.ok(list);
    }

    // параметр id передаются не в BODY запроса, а в самом URL
    @PostMapping("/id")
    public ResponseEntity<Category> findById(@RequestBody Long id) {

        Category category = null;

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            category = categoryService.findById(id);
        } catch (NoSuchElementException e) { // если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(category);
    }

}