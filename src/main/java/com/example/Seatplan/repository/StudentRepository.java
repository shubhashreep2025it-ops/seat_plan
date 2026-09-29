package com.example.seatplan.repository;

import com.example.seatplan.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Check if a student with the given register number exists
    boolean existsByRegisterNumber(String registerNumber);

    // Find a student by register number
    Optional<Student> findByRegisterNumber(String registerNumber);
}