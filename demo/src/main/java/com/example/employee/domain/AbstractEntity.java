package com.example.employee.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public abstract T getId();


    @Column(name = "created_by")
    private String createdBy;

    @Column(name="created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name="last_modified_date")
    private String lastModifiedDate;
}
