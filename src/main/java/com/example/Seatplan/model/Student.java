package com.example.seatplan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Register number is required")
    @Column(unique = true, nullable = false)
    private String registerNumber;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Class/Section is required")
    private String className; // e.g., "CSE-A", "ECE-B"

    public Student() {
    }

    public Student(String registerNumber, String name, String className) {
        this.registerNumber = registerNumber;
        this.name = name;
        this.className = className;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}