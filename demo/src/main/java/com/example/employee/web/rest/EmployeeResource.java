package com.example.employee.web.rest;

import com.example.employee.dto.ResponseDTO;
import com.example.employee.dto.employee.EmployeeDTO;
import com.example.employee.dto.employee.crud.EmployeeCreateDTO;
import com.example.employee.dto.employee.crud.EmployeeSearchDTO;
import com.example.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/sorted")
    public ResponseDTO<List<EmployeeDTO>> getAllEmployeesSort(@PageableDefault(sort = "department", direction = Sort.Direction.ASC) Pageable pageable) {
        return employeeService.getAllEmployees(pageable);
    }

    @GetMapping("/search")
    public ResponseDTO<List<EmployeeDTO>> searchEmployee(EmployeeSearchDTO employeeSearchDTO){
        return employeeService.searchEmployee(employeeSearchDTO);
    }

    @GetMapping("/{id}")
    public ResponseDTO<EmployeeDTO> getById(@PathVariable("id") Long id){
        return employeeService.getById(id);
    }



    @PostMapping("/{id}/upload")
    public ResponseEntity<ResponseDTO<Void>> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        ResponseDTO<Void> response = employeeService.uploadImage(id, file);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-and-update/{id}")
    public ResponseEntity<ResponseDTO<Void>> updateAndUpdate(@PathVariable Long id) {
        ResponseDTO<Void> response = employeeService.updateEmployeeAndUpdateTodo(id);
        return ResponseEntity.ok(response);
    }



}
