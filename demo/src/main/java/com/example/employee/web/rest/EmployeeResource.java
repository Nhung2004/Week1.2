package com.example.employee.web.rest;

import com.example.employee.dto.ResponseDTO;
import com.example.employee.dto.employee.EmployeeDTO;
import com.example.employee.dto.employee.crud.EmployeeCreateDTO;
import com.example.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeResource {
    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("")
    public ResponseDTO<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeCreateDTO employeeCreateDTO) {
        return employeeService.createEmployee(employeeCreateDTO);
    }

    @GetMapping("")
    public ResponseDTO<List<EmployeeDTO>> getAllEmployees(Pageable pageable) {
        return employeeService.getAllEmployees(pageable);
    }

    @GetMapping("/{id}")
    public ResponseDTO<EmployeeDTO> getById(@PathVariable("id") String id){
        return employeeService.getById(id);
    }


}
