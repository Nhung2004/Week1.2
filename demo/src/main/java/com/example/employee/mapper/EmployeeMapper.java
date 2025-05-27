package com.example.employee.mapper;

import com.example.employee.domain.EmployeeEntity;
import com.example.employee.dto.employee.EmployeeDTO;
import com.example.employee.dto.employee.crud.EmployeeCreateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeCreateDTO toCreateDTO(EmployeeEntity entity);


    EmployeeDTO toDTO(EmployeeEntity entity);

    EmployeeEntity toEntity(EmployeeCreateDTO dto);
}
