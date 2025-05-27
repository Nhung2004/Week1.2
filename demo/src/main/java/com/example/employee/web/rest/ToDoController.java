package com.example.employee.web.rest;


import com.example.employee.dto.ResponseDTO;
import com.example.employee.dto.todo.ToDoDTO;
import com.example.employee.service.ToDoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping
    public ResponseDTO<ToDoDTO> createToDo(@RequestBody ToDoDTO toDoDTO) {
        return toDoService.createToDo(toDoDTO);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseDTO<List<ToDoDTO>> getToDosByEmployee(@PathVariable String employeeId) {
        return toDoService.getToDosByEmployeeId(employeeId);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteToDo(@PathVariable Long id) {
        return toDoService.deleteToDo(id);
    }

    @PutMapping
    public ResponseDTO<ToDoDTO> updateToDo(@RequestBody ToDoDTO toDoDTO) {
        return toDoService.updateToDo(toDoDTO);
    }
}
