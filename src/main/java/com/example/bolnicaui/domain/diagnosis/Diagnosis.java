package com.example.bolnicaui.domain.diagnosis;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "diagnosis")
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "diagnosis", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<PatientDiagnosisHistory> patientDiagnosisHistories;
    public void addPatientDiagnosisHistory(PatientDiagnosisHistory patientDiagnosisHistory) {
        if(patientDiagnosisHistory == null)
            return;
        if(patientDiagnosisHistories == null)
            patientDiagnosisHistories = new ArrayList<>();
        patientDiagnosisHistories.add(patientDiagnosisHistory);
        patientDiagnosisHistory.setDiagnosis(this);
    }
}
