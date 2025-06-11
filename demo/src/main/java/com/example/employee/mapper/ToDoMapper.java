package com.example.employee.mapper;


import com.example.employee.domain.todo.ToDoEntity;
import com.example.employee.dto.todo.ToDoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ToDoMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    ToDoDTO toDTO(ToDoEntity entity);

    @Mapping(source = "employeeId", target = "employee.id")
    ToDoEntity toEntity(ToDoDTO dto);
}
