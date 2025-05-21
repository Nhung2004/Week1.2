package com.example.demo.mapper;

import com.example.demo.domain.EmployeeEntity;
import com.example.demo.dto.employee.EmployeeDTO;
import com.example.demo.dto.employee.crud.EmployeeCreateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeCreateDTO toCreateDTO(EmployeeEntity entity);


    EmployeeDTO toDTO(EmployeeEntity entity);

    EmployeeEntity toEntity(EmployeeCreateDTO dto);
}
