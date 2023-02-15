package com.example.bolnicaui.domain.rooms;

import com.example.bolnicaui.domain.Doctor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "doctor_department")
public class DoctorDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "date_from")
    private LocalDate dateFrom;
    @Column(name = "date_to")
    private LocalDate dateTo;

    /**
     * True if doctor is head (chief) doctor for that department
     */
    @Column(name = "head_doctor")
    private boolean headDoctor;
    /**
     * True if department is main for that doctor
     */
    @Column(name = "main_department")
    private boolean mainDepartment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
