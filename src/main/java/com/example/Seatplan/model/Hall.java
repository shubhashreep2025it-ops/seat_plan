package com.example.seatplan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "halls")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Hall name is required")
    private String hallName;

    @Min(value = 1, message = "Total rows must be at least 1")
    private int totalRows;

    @Min(value = 1, message = "Total columns must be at least 1")
    private int totalColumns;

    @Min(value = 1, message = "Capacity must be positive")
    private int capacity;

    public Hall() {
    }

    public Hall(String hallName, int totalRows, int totalColumns, int capacity) {
        this.hallName = hallName;
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.capacity = capacity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}