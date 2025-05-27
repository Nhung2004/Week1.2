package com.example.employee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="employee")
public class EmployeeEntity {

    @Id
    private String id;


    @NotBlank(message = "Tên không được để trống!")
    @Column(name = "name")
    private String name;

    @Min(value=17,message = "Tuổi phải lớn hơn hoặc bằng 17")
    @Column(name = "age")
    private Integer age;

    @NotBlank(message = "Mức lương không được để trống!")
    @Column(name = "salary")
    private BigDecimal salary;

    @NotBlank(message = "Phòng ban không được để trống!")
    @Column(name = "department")
    private String department;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeeEntity> todos;


}
