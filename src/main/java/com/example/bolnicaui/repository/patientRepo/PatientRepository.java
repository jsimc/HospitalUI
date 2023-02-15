package com.example.bolnicaui.repository.patientRepo;

import com.example.bolnicaui.domain.Patient;
import com.example.bolnicaui.dto.PatientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query(nativeQuery = true,
    value = "select count(distinct p.id) from covid_report cr " +
            "join patient p on(p.id = cr.patient_id) " +
            "join drug_history dh on(dh.patient_id = p.id) " +
            "join drug d on (dh.drug_id = d.id) " +
            "join drug_type dt on(d.drug_type_id = dt.id) " +
            "where dt.type like 'antibiotik' and " +
            "cr.date_from <= dh.date_from and cr.date_to >= dh.date_to;")
    Integer prviQuery();
    @Query(nativeQuery = true,
    value = "select count(distinct cr.patient_id) from covid_report cr;")
    Integer drugiQuery();
}
