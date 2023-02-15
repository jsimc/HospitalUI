package com.example.bolnicaui.repository.doctorRepo;

import com.example.bolnicaui.domain.ChosenDoctor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
@Repository
public interface ChosenDoctorRepository extends JpaRepository<ChosenDoctor, Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true,
    value = "update chosen_doctor " +
            "set currently = ?1, " +
            "from_date = ?2, " +
            "to_date = ?3, " +
            "doctor_id = ?4," +
            "patient_id = ?5 " +
            "where id = ?6")
    void updateChosenDoctor(boolean isCurrently, LocalDate fromDate, LocalDate toDate, Long doctorId, Long patientId, Long chosenDoctorId);
}