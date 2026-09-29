package com.example.seatplan.service;

import com.example.seatplan.model.Hall;
import com.example.seatplan.model.SeatAllocation;
import com.example.seatplan.model.Student;
import com.example.seatplan.repository.HallRepository;
import com.example.seatplan.repository.SeatAllocationRepository;
import com.example.seatplan.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SeatAllocationService {

    private final HallRepository hallRepository;
    private final StudentRepository studentRepository;
    private final SeatAllocationRepository allocationRepository;

    public SeatAllocationService(HallRepository hallRepository,
                                 StudentRepository studentRepository,
                                 SeatAllocationRepository allocationRepository) {
        this.hallRepository = hallRepository;
        this.studentRepository = studentRepository;
        this.allocationRepository = allocationRepository;
    }

    @Transactional
    public List<SeatAllocation> generateSeatingPlan(Long hallId, List<Long> studentIds, String sessionName) {
        // Fetch hall details
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new IllegalArgumentException("Hall not found with ID: " + hallId));

        // Fetch students
        List<Student> students = studentRepository.findAllById(studentIds);

        // 1. Capacity Check Rule
        if (students.size() > hall.getCapacity()) {
            throw new IllegalArgumentException("Selected students count (" + students.size() +
                    ") exceeds hall capacity (" + hall.getCapacity() + ")");
        }

        // Group students by class/section to avoid placing same class students adjacently
        Map<String, Queue<Student>> classMap = new HashMap<>();
        for (Student student : students) {
            classMap.computeIfAbsent(student.getClassName(), k -> new LinkedList<>()).add(student);
        }

        // Interleave students across classes
        List<Student> interleavedStudents = interleaveByClass(classMap);

        // Clear existing allocations for this hall and session before regenerating
        allocationRepository.deleteByHallIdAndSessionName(hallId, sessionName);

        List<SeatAllocation> allocations = new ArrayList<>();
        int index = 0;

        // Assign seats in row-column matrix format
        for (int r = 1; r <= hall.getTotalRows(); r++) {
            for (int c = 1; c <= hall.getTotalColumns(); c++) {
                if (index >= interleavedStudents.size()) break;

                Student studentToPlace = interleavedStudents.get(index++);
                SeatAllocation allocation = new SeatAllocation(studentToPlace, hall, r, c, sessionName);
                allocations.add(allocation);
            }
        }

        return allocationRepository.saveAll(allocations);
    }

    // Round-robin interleaving algorithm to separate students of the same class
    private List<Student> interleaveByClass(Map<String, Queue<Student>> classMap) {
        List<Student> result = new ArrayList<>();
        List<String> classes = new ArrayList<>(classMap.keySet());

        while (!classMap.isEmpty()) {
            Iterator<String> iterator = classes.iterator();
            while (iterator.hasNext()) {
                String className = iterator.next();
                Queue<Student> queue = classMap.get(className);
                if (queue != null && !queue.isEmpty()) {
                    result.add(queue.poll());
                }
                if (queue == null || queue.isEmpty()) {
                    classMap.remove(className);
                    iterator.remove();
                }
            }
        }
        return result;
    }

    // Get printable seating chart per hall
    public List<SeatAllocation> getHallSeatingChart(Long hallId, String sessionName) {
        return allocationRepository.findByHallIdAndSessionName(hallId, sessionName);
    }

    // Search seat assignment for a specific student
    public SeatAllocation getStudentSeat(String registerNumber, String sessionName) {
        return allocationRepository.findByStudentRegisterNumberAndSessionName(registerNumber, sessionName)
                .orElseThrow(() -> new IllegalArgumentException("No seat allocation found for student: " + registerNumber));
    }
}