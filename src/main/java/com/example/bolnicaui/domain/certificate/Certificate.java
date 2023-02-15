package com.example.bolnicaui.domain.certificate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "certificate")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "code", unique = true)
    private String code;
    @Column(name = "duration_in_days")
    private int durationInDays;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_procedure_id")
    private MedicalProcedure medicalProcedure;
    @OneToMany(mappedBy = "certificate")
    private List<DoctorCertificate> doctorCertificates;
}
