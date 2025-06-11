package com.example.employee.web.rest;


import com.example.employee.dto.ResponseDTO;
import com.example.employee.dto.todo.ToDoDTO;
import com.example.employee.dto.todo.crud.ToDoFilterDTO;
import com.example.employee.service.ToDoService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class ToDoResource {

    private final ToDoService toDoService;

    public ToDoResource(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping
    public ResponseDTO<ToDoDTO> createToDo(@RequestBody ToDoDTO toDoDTO) {
        return toDoService.createToDo(toDoDTO);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseDTO<List<ToDoDTO>> getToDosByEmployee(@PathVariable Long employeeId) {
        return toDoService.getToDosByEmployeeId(employeeId);
    }

    @PostMapping("/employee/filter")
    public ResponseDTO<List<ToDoDTO>> filterToDos(@RequestBody ToDoFilterDTO filterDTO) {
        return toDoService.filterToDos(filterDTO);
    }

    /**
     * Retrieves all ToDo items with pagination support.
     *
     * @param pageable the pagination information
     * @return a ResponseDTO containing a list of all ToDoDTOs
     */
    @GetMapping("/all")
    public ResponseDTO<List<ToDoDTO>> getAllToDos(Pageable pageable) {
        return toDoService.getAllToDos(pageable);
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
