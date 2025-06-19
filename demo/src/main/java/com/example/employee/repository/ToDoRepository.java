package com.example.employee.repository;

import com.example.employee.domain.todo.ToDoEntity;
import com.example.employee.enums.ToDoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ToDoRepository extends JpaRepository<ToDoEntity, Long> {

    Optional<ToDoEntity> findByEmployeeId(Long employeeId);

    @Query("SELECT t FROM ToDoEntity t " +
            "WHERE (CAST(:status AS string) IS NULL OR t.status = :status) " +
            "AND (CAST(:startDate AS timestamp) IS NULL OR t.createdDate >= :startDate) " +
            "AND (CAST(:endDate AS timestamp) IS NULL OR t.createdDate <= :endDate)")
    List<ToDoEntity> filterToDos(
            @Param("status") ToDoStatus status,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);



    void deleteByEmployeeId(Long id);


}
