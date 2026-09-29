package com.example.seatplan.repository;

import com.example.seatplan.model.SeatAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatAllocationRepository extends JpaRepository<SeatAllocation, Long> {

    // Fetch printable seating chart for a specific hall and session
    List<SeatAllocation> findByHallIdAndSessionName(Long hallId, String sessionName);

    // Look up a student's allocated hall and seat number
    Optional<SeatAllocation> findByStudentRegisterNumberAndSessionName(String registerNumber, String sessionName);

    // Delete existing allocations before re-generating a plan for a hall and session
    void deleteByHallIdAndSessionName(Long hallId, String sessionName);
}