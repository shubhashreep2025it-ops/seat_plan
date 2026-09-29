package com.example.seatplan.controller;

import com.example.seatplan.model.SeatAllocation;
import com.example.seatplan.service.SeatAllocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allocations")
public class SeatAllocationController {

    private final SeatAllocationService allocationService;

    public SeatAllocationController(SeatAllocationService allocationService) {
        this.allocationService = allocationService;
    }

    // Auto-generate seating arrangement for a specific hall and session
    @PostMapping("/generate")
    public ResponseEntity<List<SeatAllocation>> generatePlan(
            @RequestParam Long hallId,
            @RequestBody List<Long> studentIds,
            @RequestParam String sessionName) {

        List<SeatAllocation> result = allocationService.generateSeatingPlan(hallId, studentIds, sessionName);
        return ResponseEntity.ok(result);
    }

    // Retrieve printable seating chart for a specific hall and session
    @GetMapping("/hall/{hallId}")
    public ResponseEntity<List<SeatAllocation>> getSeatingChart(
            @PathVariable Long hallId,
            @RequestParam String sessionName) {

        List<SeatAllocation> chart = allocationService.getHallSeatingChart(hallId, sessionName);
        return ResponseEntity.ok(chart);
    }

    // Search allocated seat for a specific student using register number
    @GetMapping("/student/{registerNumber}")
    public ResponseEntity<SeatAllocation> getStudentAssignment(
            @PathVariable String registerNumber,
            @RequestParam String sessionName) {

        SeatAllocation allocation = allocationService.getStudentSeat(registerNumber, sessionName);
        return ResponseEntity.ok(allocation);
    }
}