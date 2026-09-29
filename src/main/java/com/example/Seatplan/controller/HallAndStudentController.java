package com.example.seatplan.controller;

import com.example.seatplan.model.Hall;
import com.example.seatplan.model.Student;
import com.example.seatplan.repository.HallRepository;
import com.example.seatplan.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HallAndStudentController {

    private final HallRepository hallRepository;
    private final StudentRepository studentRepository;

    public HallAndStudentController(HallRepository hallRepository, StudentRepository studentRepository) {
        this.hallRepository = hallRepository;
        this.studentRepository = studentRepository;
    }

    // Register a new exam hall
    @PostMapping("/halls")
    public ResponseEntity<Hall> createHall(@Valid @RequestBody Hall hall) {
        Hall savedHall = hallRepository.save(hall);
        return new ResponseEntity<>(savedHall, HttpStatus.CREATED);
    }

    // Get all exam halls
    @GetMapping("/halls")
    public ResponseEntity<List<Hall>> getAllHalls() {
        return ResponseEntity.ok(hallRepository.findAll());
    }

    // Register a new student
    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    // Get all registered students
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }
}