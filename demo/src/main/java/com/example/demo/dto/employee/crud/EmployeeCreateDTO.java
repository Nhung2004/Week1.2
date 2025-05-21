package com.example.demo.dto.employee.crud;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeCreateDTO {

    private String name;

    private String age;

    private BigDecimal salary;

    private String department;
}
