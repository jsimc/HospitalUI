package com.example.bolnicaui.repository.diagnosisRepo;

import com.example.bolnicaui.domain.diagnosis.PatientDiagnosisHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface PatientDiagnosisHistoryRepository extends JpaRepository<PatientDiagnosisHistory, Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true,
    value = "UPDATE `bolnicaui`.`patient_diagnosis_history` SET `date_to` = ?2 WHERE (`id` = ?1);")
    void updateIsActiveAndDateTo(Long patientDiagnosisId, LocalDate dateTo);
}
