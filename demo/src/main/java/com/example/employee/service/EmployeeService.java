package com.example.employee.service;

import com.example.employee.domain.EmployeeEntity;
import com.example.employee.dto.ResponseDTO;
import com.example.employee.dto.employee.EmployeeDTO;
import com.example.employee.dto.employee.crud.EmployeeCreateDTO;
import com.example.employee.dto.employee.crud.EmployeeSearchDTO;
import com.example.employee.enums.ToDoStatus;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public ResponseDTO<EmployeeDTO> createEmployee(EmployeeCreateDTO input) {
        ResponseDTO response = new ResponseDTO<>();
        try {
            EmployeeEntity employeeEntity = employeeMapper.toEntity(input);
            employeeRepository.save(employeeEntity);
            response.setData(employeeMapper.toDTO(employeeEntity));
            response.setMessage("Success");
            response.setStatus(ToDoStatus.COMPLETED);
        } catch(Exception e){
            response.setMessage(e.getMessage());
            response.setStatus(ToDoStatus.CANCELLED);
        }
        return response;
    }


    public ResponseDTO<EmployeeDTO> getById(String id) {
        ResponseDTO<EmployeeDTO> response = new ResponseDTO<>();
        try {
            EmployeeEntity employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new Exception("Employee not found with ID: " + id));

            EmployeeDTO dto = employeeMapper.toDTO(employee);
            response.setData(dto);
            response.setMessage("Success");
            response.setStatus(ToDoStatus.COMPLETED);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(ToDoStatus.CANCELLED);
        }
        return response;
    }


    public ResponseDTO<List<EmployeeDTO>> getAllEmployees(Pageable pageable) {
        ResponseDTO<List<EmployeeDTO>> response = new ResponseDTO<>();
        try {
            Page<EmployeeEntity> page = employeeRepository.findAll(pageable);
            List<EmployeeDTO> listEmployeeDto = page.stream()
                    .map(employeeMapper::toDTO)
                    .collect(Collectors.toList());

            response.setData(listEmployeeDto);
            response.setMessage("Success");
            response.setPage(page.getNumber());
            response.setPageSize(page.getSize());
            response.setTotalElement(page.getTotalElements());
            response.setTotalPage(page.getTotalPages());
            response.setStatus(ToDoStatus.COMPLETED);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(ToDoStatus.PENDING);
        }
        return response;
    }

    public ResponseDTO<Void> deleteById(String id) {
        ResponseDTO<Void> response = new ResponseDTO<>();
        try {
            EmployeeEntity employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new Exception("Employee not found with ID: " + id));
            employeeRepository.delete(employee);
            response.setMessage("Deleted successfully");
            response.setStatus(ToDoStatus.COMPLETED);
        } catch (Exception e) {
            response.setStatus(ToDoStatus.PENDING);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    public ResponseDTO<List<EmployeeDTO>> searchEmployee(EmployeeSearchDTO searchDTO) {
        ResponseDTO<List<EmployeeDTO>> response = new ResponseDTO<>();

        PageRequest pageRequest = PageRequest.of(searchDTO.getPageIndex(), searchDTO.getPageSize());

        Page<EmployeeEntity> employeeEntities = employeeRepository.searchEmployees(
                searchDTO.getName(),
                searchDTO.getAge(),
                searchDTO.getMinSalary(),
                searchDTO.getMaxSalary(),
                searchDTO.getDepartment(),
                pageRequest
        );

        response.setTotalElement(employeeEntities.getTotalElements());
        response.setPage(pageRequest.getPageNumber());
        response.setPageSize(searchDTO.getPageSize());
        response.setTotalPage(employeeEntities.getTotalPages());

        List<EmployeeDTO> employeeDTOs = employeeEntities.stream()
                .map(employeeMapper::toDTO)
                .collect(Collectors.toList());

        response.setData(employeeDTOs);

        return response;
    }








}
