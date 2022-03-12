package com.example.micro.planner.todo.repo;

import com.example.micro.planner.entity.Stat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
public interface StatRepository extends CrudRepository<Stat, Long> {
    Stat findByUserId(Long userId); // всегда получаем только 1 объект, т.к. 1 пользователь содержит только 1 строку статистики (связь "один к одному")
}
