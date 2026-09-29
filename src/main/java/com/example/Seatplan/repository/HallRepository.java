package com.example.seatplan.repository;

import com.example.seatplan.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {

    // Find hall details by hall name
    Optional<Hall> findByHallName(String hallName);
}