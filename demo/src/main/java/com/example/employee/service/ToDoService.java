package com.example.employee.service;


import com.example.employee.domain.EmployeeEntity;
import com.example.employee.domain.ToDoEntity;
import com.example.employee.dto.ResponseDTO;
import com.example.employee.dto.todo.ToDoDTO;
import com.example.employee.dto.todo.crud.ToDoUpdateStatusDTO;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.mapper.ToDoMapper;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final EmployeeRepository employeeRepository;
    private final ToDoMapper toDoMapper;

    public ToDoService(ToDoRepository toDoRepository,
                       EmployeeRepository employeeRepository,
                       ToDoMapper toDoMapper) {
        this.toDoRepository = toDoRepository;
        this.employeeRepository = employeeRepository;
        this.toDoMapper = toDoMapper;
    }

    public ResponseDTO<ToDoDTO> createToDo(ToDoDTO input) {
        ResponseDTO<ToDoDTO> response = new ResponseDTO<>();
        try {
            EmployeeEntity employee = employeeRepository.findById(input.getEmployeeId())
                    .orElseThrow(() -> new Exception("Employee not found with id: " + input.getEmployeeId()));

            ToDoEntity toDoEntity = toDoMapper.toEntity(input);
            toDoEntity.setEmployee(employee);

            toDoEntity = toDoRepository.save(toDoEntity);

            response.setData(toDoMapper.toDTO(toDoEntity));
            response.setMessage("Success");
        } catch (Exception e) {
            response.setStatus("400");
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public ResponseDTO<List<ToDoDTO>> getToDosByEmployeeId(String employeeId) {
        ResponseDTO<List<ToDoDTO>> response = new ResponseDTO<>();
        try {
            List<ToDoEntity> toDos = toDoRepository.findByEmployeeId(employeeId);
            List<ToDoDTO> toDoDTOs = toDos.stream()
                    .map(toDoMapper::toDTO)
                    .collect(Collectors.toList());

            response.setData(toDoDTOs);
            response.setMessage("Success");
        } catch (Exception e) {
            response.setStatus("400");
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public ResponseDTO<Void> deleteToDo(Long id) {
        ResponseDTO<Void> response = new ResponseDTO<>();
        try {
            ToDoEntity toDoEntity = toDoRepository.findById(id)
                    .orElseThrow(() -> new Exception("ToDo not found with id: " + id));
            toDoRepository.delete(toDoEntity);
            response.setMessage("Deleted successfully");
        } catch (Exception e) {
            response.setStatus("400");
            response.setMessage(e.getMessage());
        }
        return response;
    }

    // Update task (optional)
    public ResponseDTO<ToDoDTO> updateToDo(ToDoDTO input) {
        ResponseDTO<ToDoDTO> response = new ResponseDTO<>();
        try {
            ToDoEntity existing = toDoRepository.findById(input.getId())
                    .orElseThrow(() -> new Exception("ToDo not found with id: " + input.getId()));

            existing.setTask(input.getTask());
            existing.getStatus(input.getStatus());

            toDoRepository.save(existing);
            response.setData(toDoMapper.toDTO(existing));
            response.setMessage("Updated successfully");
        } catch (Exception e) {
            response.setStatus("400");
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public Optional<ToDoEntity> updateTaskStatus(Long taskId, ToDoUpdateStatusDTO dto) {
        ToDoEntity task = toDoRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task không tồn tại với id = " + taskId));
        task.setStatus(dto.getStatus());
        return Optional.of(toDoRepository.save(task));
    }

}
