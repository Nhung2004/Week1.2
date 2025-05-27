package com.example.employee.dto.todo.crud;

import com.example.employee.enums.ToDoStatus;
import com.example.employee.web.rest.ToDoController;
import lombok.Getter;
import lombok.Setter;


public class ToDoUpdateStatusDTO {
    private ToDoStatus status;

    public ToDoStatus getStatus() {
        return status;
    }

    public void setStatus(ToDoStatus status) {
        this.status = status;
    }
}
