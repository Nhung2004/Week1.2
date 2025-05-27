package com.example.employee.repository;

import com.example.employee.domain.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,String> {

    @Query(value = "SELECT * FROM employee e " +
            "WHERE (:name IS NULL OR LOWER(REPLACE(e.name, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:name, ' ', ''), '%'))) " +
            "AND (:age IS NULL OR e.age = :age) " +
            "AND (:minSalary IS NULL OR e.salary >= :minSalary) " +
            "AND (:maxSalary IS NULL OR e.salary <= :maxSalary) " +
            "AND (:department IS NULL OR LOWER(e.department) = LOWER(:department))",
            nativeQuery = true)
    Page<EmployeeEntity> searchEmployees(
            @Param("name") String name,
            @Param("age") Integer age,
            @Param("minSalary") BigDecimal minSalary,
            @Param("maxSalary") BigDecimal maxSalary,
            @Param("department") String department,
            Pageable pageable
    );


}
