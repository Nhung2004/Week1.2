package com.example.employee.dto;

import com.example.employee.enums.ToDoStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ResponseDTO<T> {
    private T data;

    private ToDoStatus status;

    private String message;

    private Integer page;

    private Integer pageSize;

    private Integer totalPage;

    private Long totalElement;



    public T getData() {
        return data;
    }


    public String getMessage() {
        return message;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Long getTotalElement() {
        return totalElement;
    }


    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public void setTotalElement(Long totalElement) {
        this.totalElement = totalElement;
    }

    public ToDoStatus getStatus() {
        return status;
    }

    public void setStatus(ToDoStatus status) {
        this.status = status;
    }
}
