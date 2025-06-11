package com.example.employee.dto.todo.crud;

import com.example.employee.enums.ToDoStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToDoFilterDTO {

    private ToDoStatus status;

    private Instant startDate;

    private Instant endDate;

}
