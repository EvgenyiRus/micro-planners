package com.example.micro.planner.users.service;

import com.example.micro.planner.entity.User;
import com.example.micro.planner.users.repo.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
// все методы класса должны выполниться без ошибки, чтобы транзакция завершилась
// если в методе возникнет исключение - все выполненные операции откатятся (Rollback)
@Transactional
public class UserService {

    private final UserRepository repository; // сервис имеет право обращаться к репозиторию (БД)

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User add(User user) {
        return repository.save(user); // метод save обновляет или создает новый объект, если его не было
    }

    public User update(User user) {
        return repository.save(user); // метод save обновляет или создает новый объект, если его не было
    }

    public void deleteByUserId(Long id) {
        repository.deleteById(id);
    }

    public void deleteByUserEmail(String email) {
        repository.deleteByEmail(email);
    }

    public User findById(Long id) {
        return repository.findById(id).get(); // т.к. возвращается Optional - можно получить объект методом get()
    }

    public Page<User> findByParams(String username, String password, PageRequest paging) {
        return repository.findByParams(username, password, paging);
    }
}
