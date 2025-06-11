package com.example.employee.dto.employee.crud;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeCreateDTO {

    @NotBlank(message = "Tên không được để trống!")

    private String name;

    @Min(value=17,message = "Tuổi phải lớn hơn hoặc bằng 17")
    private String age;

      @NotNull
    private BigDecimal salary;

    @NotBlank(message = "Phòng ban không được để trống!")

    private String department;
}
