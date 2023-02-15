package com.example.bolnicaui.repository.patientRepo;

import com.example.bolnicaui.domain.CovidReport;
import com.example.bolnicaui.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidReportRepository extends JpaRepository<CovidReport, Long> {
    CovidReport findCovidReportByPatientAndCurrentlySickIsTrue(Patient patient);
}
