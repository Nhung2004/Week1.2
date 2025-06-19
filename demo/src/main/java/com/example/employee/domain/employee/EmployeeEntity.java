package com.example.employee.domain.employee;

import com.example.employee.domain.todo.ToDoEntity;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;


    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "department")
    private String department;

    @Column(name = "avatar")
    private String avatar;

    @Column(name="image_name")
    private String imageName;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<ToDoEntity> todos;



// mappedBy cho biết Todoentity chưa Id của employee
    //cascade: khi tạo mới employee thì sẽ tự động tạo todo
    //CascadeType.ALL: tất cả các thao tác trên employee sẽ truyền xuống todo
    //CascadeType.PERSIST: chỉ tạo mới
    //CascadeType.MERGE: chỉ cập nhật
    //CascadeType.REMOVE: chỉ xoá
    //CascadeType.DETACH: chỉ tách ra khỏi session
    //CascadeType.REFRESH: chỉ refresh lại

    //FetchType.EAGER: lấy dữ liệu ngay lập tức
    //FetchType.LAZY: lấy dữ liệu khi cần thiết

}
