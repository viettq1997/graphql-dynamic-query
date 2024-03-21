package com.viettq.querydsldynamicquery;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "student")
@Getter
@Setter
public class Student {

    @Id
    private Long id;
    private Integer age;
    private LocalDate dob;
    private String email;
    private String name;
}
