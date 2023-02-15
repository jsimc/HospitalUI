package com.example.bolnicaui.domain.insurance;

import com.example.bolnicaui.domain.Patient;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "patient_insurance")
public class PatientInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;
    @Column(name = "date_to")
    private LocalDate dateTo;
    @Column(name = "valid")
    private boolean valid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_insurance_id")
    private HealthInsurance healthInsurance;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @Override
    public String toString() {
        return "PatientInsurance{" +
                "valid=" + valid +
                ", healthInsurance=" + healthInsurance +
                ", patient=" + patient +
                '}';
    }
}
