package com.example.employee.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeDTO {
    private String id;
    private String name;
    private Integer age;
    private BigDecimal salary;
    private String department;

}
