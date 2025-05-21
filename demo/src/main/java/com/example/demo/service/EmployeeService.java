package com.example.demo.service;

import com.example.demo.domain.EmployeeEntity;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.employee.EmployeeDTO;
import com.example.demo.dto.employee.crud.EmployeeCreateDTO;
import com.example.demo.dto.employee.crud.EmployeeSearchDTO;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
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
        } catch(Exception e){
            response.setStatus("400");
            response.setMessage(e.getMessage());
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
        } catch (Exception e) {
            response.setStatus("400");
            response.setMessage(e.getMessage());
        }
        return response;
    }


    public ResponseDTO<List<EmployeeDTO>> getAllEmployees() {
        ResponseDTO<List<EmployeeDTO>> response = new ResponseDTO<>();
        try {
            List<EmployeeEntity> employees = employeeRepository.findAll();
            if (employees.isEmpty()) {
                throw new Exception("No Employees Found");
            }

            List<EmployeeDTO> listEmployeeDto = employees.stream()
                    .map(employeeMapper::toDTO)
                    .collect(Collectors.toList());

            response.setData(listEmployeeDto);
            response.setMessage("Success");
        } catch (Exception e) {
            response.setStatus("400");
            response.setMessage(e.getMessage());
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
        } catch (Exception e) {
            response.setStatus("400");
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
