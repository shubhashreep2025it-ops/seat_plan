package com.example.seatplan.model;

import jakarta.persistence.*;

@Entity
@Table(name = "seat_allocations")
public class SeatAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    private int rowNo;
    private int colNo;
    private String sessionName; // e.g., "FN" or "AN"

    public SeatAllocation() {
    }

    public SeatAllocation(Student student, Hall hall, int rowNo, int colNo, String sessionName) {
        this.student = student;
        this.hall = hall;
        this.rowNo = rowNo;
        this.colNo = colNo;
        this.sessionName = sessionName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public int getColNo() {
        return colNo;
    }

    public void setColNo(int colNo) {
        this.colNo = colNo;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }
}