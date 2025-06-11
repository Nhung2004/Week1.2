package com.example.employee.domain.todo;

import com.example.employee.domain.AbstractEntity;
import com.example.employee.domain.employee.EmployeeEntity;
import com.example.employee.enums.ToDoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name="todo")
public class ToDoEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task")
    private String task;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ToDoStatus status;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public ToDoStatus getStatus() {
        return this.status;
    }

    public void setStatus(ToDoStatus status) {
        this.status = this.status;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
}
