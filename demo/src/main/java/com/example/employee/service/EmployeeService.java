package com.example.employee.service;

import com.example.employee.domain.employee.EmployeeEntity;
import com.example.employee.domain.todo.ToDoEntity;
import com.example.employee.dto.ResponseDTO;
import com.example.employee.dto.employee.EmployeeDTO;
import com.example.employee.dto.employee.crud.EmployeeCreateDTO;
import com.example.employee.dto.employee.crud.EmployeeSearchDTO;
import com.example.employee.dto.todo.ToDoDTO;
import com.example.employee.enums.ToDoStatus;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.ToDoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ToDoRepository todoRepository;
    private final ToDoService toDoService;
    private ToDoDTO dto;
    private final String uploadDir = "uploads/";

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, ToDoRepository todoRepository, ToDoService toDoService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.todoRepository = todoRepository;
        this.toDoService = toDoService;
    }


    public ResponseDTO<EmployeeDTO> createEmployee(EmployeeCreateDTO input) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(input);
        employeeRepository.save(employeeEntity);
        return ResponseDTO.<EmployeeDTO>builder()
                .data(employeeMapper.toDTO(employeeEntity))
                .message("Success")
                .status(ToDoStatus.COMPLETED)
                .build();
    }

    public ResponseDTO<EmployeeDTO> getById(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        EmployeeDTO dto = employeeMapper.toDTO(employee);
        return ResponseDTO.<EmployeeDTO>builder()
                .data(dto)
                .message("Success")
                .status(ToDoStatus.COMPLETED)
                .build();
    }

    public ResponseDTO<List<EmployeeDTO>> getAllEmployees(Pageable pageable) {
        try {
            Page<EmployeeEntity> page = employeeRepository.findAll(pageable);
            List<EmployeeDTO> listEmployeeDto = page.stream()
                    .map(employeeMapper::toDTO)
                    .collect(Collectors.toList());

            return ResponseDTO.<List<EmployeeDTO>>builder()
                    .data(listEmployeeDto)
                    .message("Success")
                    .page(page.getNumber())
                    .pageSize(page.getSize())
                    .totalElement(page.getTotalElements())
                    .totalPage(page.getTotalPages())
                    .status(ToDoStatus.COMPLETED)
                    .build();

        } catch (Exception e) {
            return ResponseDTO.<List<EmployeeDTO>>builder()
                    .message(e.getMessage())
                    .status(ToDoStatus.PENDING)
                    .build();
        }
    }




    public ResponseDTO<Void> deleteById(Long id) {
        try {
            EmployeeEntity employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new Exception("Employee not found with ID: " + id));
            employeeRepository.delete(employee);
            return ResponseDTO.<Void>builder()
                    .message("Deleted successfully")
                    .status(ToDoStatus.COMPLETED)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.<Void>builder()
                    .message(e.getMessage())
                    .status(ToDoStatus.PENDING)
                    .build();
        }
    }

    public ResponseDTO<List<EmployeeDTO>> searchEmployee(EmployeeSearchDTO searchDTO) {

        Sort sort = Sort.by(Sort.Direction.DESC, "age");
        PageRequest pageRequest = PageRequest.of(searchDTO.getPageIndex(), searchDTO.getPageSize());
        Page<EmployeeEntity> employeeEntities = employeeRepository.searchEmployees(
                searchDTO.getName(),
                searchDTO.getAge(),
                searchDTO.getMinSalary(),
                searchDTO.getMaxSalary(),
                searchDTO.getDepartment(),
                pageRequest
        );

        List<EmployeeDTO> employeeDTOs = employeeEntities.stream()
                .map(employeeMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseDTO.<List<EmployeeDTO>>builder()
                .data(employeeDTOs)
                .totalElement(employeeEntities.getTotalElements())
                .page(pageRequest.getPageNumber())
                .pageSize(searchDTO.getPageSize())
                .totalPage(employeeEntities.getTotalPages())
                .status(ToDoStatus.COMPLETED)
                .build();
    }


    @Transactional
    public ResponseDTO<Void> uploadImage(Long id, MultipartFile file) {
        try {
            EmployeeEntity employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee không tồn tại: " + id));

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            employee.setImageName(fileName);
            employeeRepository.save(employee);

            return ResponseDTO.<Void>builder()
                    .message("Upload ảnh thành công")
                    .status(ToDoStatus.COMPLETED)
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload ảnh: " + e.getMessage());
        }
    }
    // vidu về transactional
    /// /////////////////////////////////////////////////////////////
    /// 1. Tạo employee mới
//    2. Tạo task mặc định cho employee đó
//    3. Trả response

    @Transactional
    public ResponseDTO<Void> createEmployeeWithDefaultTodo(EmployeeCreateDTO dto) {
        EmployeeEntity employee = employeeMapper.toEntity(dto);
        employeeRepository.save(employee);

        ToDoEntity todo = new ToDoEntity();
        todo.setStatus(ToDoStatus.PENDING);
        todo.setEmployee(employee);
        todo.setTask("Task của ngày hôm nay");

        todoRepository.save(todo);

        // 3. Trả response
        return ResponseDTO.<Void>builder()
                .message("tạo nhân viên và gắn việc thành công!")
                .status(ToDoStatus.COMPLETED)
                .build();
    }

    @Transactional
    public ResponseDTO<Void> updateandDelete(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee ko tồn tại: " + id));

        EmployeeEntity em = new EmployeeEntity();
        em.setName(employee.getName());
        em.setAge(employee.getAge());
        em.setDepartment(employee.getDepartment());
        em.setSalary(employee.getSalary());

        employeeRepository.save(em);

        employeeRepository.delete(employee);

        return ResponseDTO.<Void>builder()
                .message("Saved temp and deleted original")
                .status(ToDoStatus.COMPLETED)
                .build();
    }

//    @Transactional
//    public ResponseDTO<Void> createEmployeeWithTodoViaService(EmployeeCreateDTO edto) {
//        EmployeeEntity employee = employeeMapper.toEntity(edto);
//        employeeRepository.save(employee);
//
//        // 👇 Gọi hàm bên ToDoService
//        toDoService.createToDo(dto);
//
//        return ResponseDTO.<Void>builder()
//                .message("Tạo employee và gán task thành công")
//                .status(ToDoStatus.COMPLETED)
//                .build();
//    }

    //@Transactional
    public ResponseDTO<Void> updateEmployeeAndUpdateTodo(Long id){
        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee không tồn tại " + id));

        employeeEntity.setName("Hungdz");
        employeeEntity.setAge(employeeEntity.getAge() + 1);
        employeeRepository.save(employeeEntity);

        ToDoEntity todo = todoRepository.findByEmployeeId(id)
                .orElseThrow(() -> new ResourceNotFoundException("ToDo của employee không tồn tại"));
        todo.setStatus(ToDoStatus.valueOf("NoteFound"));
        todoRepository.save(todo);

        return ResponseDTO.<Void>builder()
                .message("Update employee và todo thành công")
                .status(ToDoStatus.COMPLETED)
                .build();
    }






}


