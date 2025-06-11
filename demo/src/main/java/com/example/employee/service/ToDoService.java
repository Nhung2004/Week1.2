package com.example.employee.service;

import com.example.employee.domain.employee.EmployeeEntity;
import com.example.employee.domain.todo.ToDoEntity;
import com.example.employee.dto.ResponseDTO;
import com.example.employee.dto.todo.ToDoDTO;
import com.example.employee.dto.todo.crud.ToDoFilterDTO;
import com.example.employee.dto.todo.crud.ToDoUpdateStatusDTO;
import com.example.employee.enums.ToDoStatus;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.mapper.ToDoMapper;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.ToDoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final EmployeeRepository employeeRepository;
    private final ToDoMapper toDoMapper;
    private final RestClient.Builder builder;

    public ToDoService(ToDoRepository toDoRepository,
                       EmployeeRepository employeeRepository,
                       ToDoMapper toDoMapper, RestClient.Builder builder) {
        this.toDoRepository = toDoRepository;
        this.employeeRepository = employeeRepository;
        this.toDoMapper = toDoMapper;
        this.builder = builder;
    }

    public ResponseDTO<ToDoDTO> createToDo(ToDoDTO input) {
        try {
            EmployeeEntity employee = employeeRepository.findById(Long.valueOf(input.getEmployeeId()))
                    .orElseThrow(() -> new Exception("Employee not found with id: " + input.getEmployeeId()));

            ToDoEntity toDoEntity = toDoMapper.toEntity(input);
            toDoEntity.setEmployee(employee);

            toDoEntity = toDoRepository.save(toDoEntity);

            return ResponseDTO.<ToDoDTO>builder()
                    .data(toDoMapper.toDTO(toDoEntity))
                    .message("Success")
                    .status(ToDoStatus.COMPLETED)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<ToDoDTO>builder()
                    .message(e.getMessage())
                    .status(ToDoStatus.IN_PROGRESS)
                    .build();
        }
    }

    public ResponseDTO<List<ToDoDTO>> getToDosByEmployeeId(Long employeeId) {
        try {
            Optional<ToDoEntity> toDos = toDoRepository.findByEmployeeId(employeeId);
            List<ToDoDTO> toDoDTOs = toDos.stream()
                    .map(toDoMapper::toDTO)
                    .collect(Collectors.toList());

            return ResponseDTO.<List<ToDoDTO>>builder()
                    .data(toDoDTOs)
                    .message("Success")
                    .status(ToDoStatus.COMPLETED)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<List<ToDoDTO>>builder()
                    .message(e.getMessage())
                    .status(ToDoStatus.CANCELLED)
                    .build();
        }
    }

    public ResponseDTO<Void> deleteToDo(Long id) {
        try {
            ToDoEntity toDoEntity = toDoRepository.findById(id)
                    .orElseThrow(() -> new Exception("ToDo not found with id: " + id));
            toDoRepository.delete(toDoEntity);

            return ResponseDTO.<Void>builder()
                    .message("Deleted successfully")
                    .status(ToDoStatus.COMPLETED)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<Void>builder()
                    .message(e.getMessage())
                    .status(ToDoStatus.CANCELLED)
                    .build();
        }
    }

    public ResponseDTO<ToDoDTO> updateToDo(ToDoDTO input) {
        try {
            ToDoEntity existing = toDoRepository.findById(input.getId())
                    .orElseThrow(() -> new Exception("ToDo not found with id: " + input.getId()));

            existing.setTask(input.getTask());
            existing.setStatus(input.getStatus());

            toDoRepository.save(existing);

            return ResponseDTO.<ToDoDTO>builder()
                    .data(toDoMapper.toDTO(existing))
                    .message("Updated successfully")
                    .status(ToDoStatus.COMPLETED)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<ToDoDTO>builder()
                    .message(e.getMessage())
                    .status(ToDoStatus.PENDING)
                    .build();
        }
    }

    public Optional<ToDoEntity> updateTaskStatus(Long taskId, ToDoUpdateStatusDTO dto) {
        ToDoEntity task = toDoRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task không tồn tại với id = " + taskId));
        task.setStatus(dto.getStatus());
        return Optional.of(toDoRepository.save(task));
    }

    public ResponseDTO<List<ToDoDTO>> filterToDos(ToDoFilterDTO filterDTO) {
        try {
            List<ToDoEntity> results = toDoRepository.filterToDos(
                    filterDTO.getStatus() != null ? ToDoStatus.valueOf(filterDTO.getStatus().name()) : null,
                    filterDTO.getStartDate(),
                    filterDTO.getEndDate()
            );

            List<ToDoDTO> dtoList = results.stream()
                    .map(toDoMapper::toDTO)
                    .collect(Collectors.toList());

            return ResponseDTO.<List<ToDoDTO>>builder()
                    .data(dtoList)
                    .status(ToDoStatus.COMPLETED)
                    .message("Filtered successfully")
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<List<ToDoDTO>>builder()
                    .message("Filter failed: " + e.getMessage())
                    .status(ToDoStatus.CANCELLED)
                    .build();
        }
    }

    public ResponseDTO<List<ToDoDTO>> getAllToDos(Pageable pageable) {
       try {
           Page<ToDoEntity> page = toDoRepository.findAll(pageable);
           List<ToDoDTO> listToDoDTO = page.getContent().stream()
                   .map(toDoMapper::toDTO)
                   .collect(Collectors.toList());

           return ResponseDTO.<List<ToDoDTO>>builder()
                   .data(listToDoDTO)
                   .message("Success")
                   .page(page.getNumber())
                   .pageSize(page.getSize())
                   .totalElement(page.getTotalElements())
                   .totalPage(page.getTotalPages())
                   .status(ToDoStatus.COMPLETED)
                   .build();
       } catch(Exception e){
           return ResponseDTO.<List<ToDoDTO>>builder()
                   .message(e.getMessage())
                   .status(ToDoStatus.CANCELLED)
                   .build();
       }

    }

}
