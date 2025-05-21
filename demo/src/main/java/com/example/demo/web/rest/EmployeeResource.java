package com.example.demo.web.rest;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.employee.EmployeeDTO;
import com.example.demo.dto.employee.crud.EmployeeCreateDTO;
import com.example.demo.service.EmployeeService;
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
    public ResponseDTO<EmployeeDTO> createEmployee(@RequestBody EmployeeCreateDTO employeeCreateDTO) {
        return employeeService.createEmployee(employeeCreateDTO);
    }

    @GetMapping("")
    public ResponseDTO<List<EmployeeDTO>> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/id")
    public ResponseDTO<EmployeeDTO> getById(@PathVariable("id") String id){
        return employeeService.getById(id);
    }


}
