package com.example.bolnicaui.repository.doctorRepo;

import com.example.bolnicaui.domain.certificate.MedicalProcedure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalProcedureRepository extends JpaRepository<MedicalProcedure, Long> {
}
