package com.example.bolnicaui.repository.drugRepo;

import com.example.bolnicaui.domain.Patient;
import com.example.bolnicaui.domain.drug.DrugHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugHistoryRepo extends JpaRepository<DrugHistory, Long> {
    List<DrugHistory> findDrugHistoriesByPatient(Patient patient);
}
