package com.example.bolnicaui.repository.diagnosisRepo;

import com.example.bolnicaui.domain.diagnosis.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
}
