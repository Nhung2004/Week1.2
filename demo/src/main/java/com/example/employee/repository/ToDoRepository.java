package com.example.employee.repository;

import com.example.employee.domain.ToDoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDoEntity, Long> {

    List<ToDoEntity> findByEmployeeId(String employeeId );
}
