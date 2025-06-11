package com.example.employee.dto.employee;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "Tên không được để trống!")
    private String name;

    @Min(value=17,message = "Tuổi phải lớn hơn hoặc bằng 17")

    private Integer age;

    @NotBlank(message = "Mức lương không được để trống!")
    private BigDecimal salary;

    @NotBlank(message = "Phòng ban không được để trống!")
    private String department;

    private String imageName;

}
