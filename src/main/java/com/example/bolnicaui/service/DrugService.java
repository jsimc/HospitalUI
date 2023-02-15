package com.example.bolnicaui.service;

import com.example.bolnicaui.dto.DrugDto;
import com.example.bolnicaui.dto.DrugHistoryDto;
import com.example.bolnicaui.exception.HospitalException;
import com.example.bolnicaui.repository.drugRepo.PharmaCompanyDrugI;

import java.util.List;

public interface DrugService {
    PharmaCompanyDrugI findMostUsedDrugFromCompanyByGenericName(String drugGenericName);
    List<DrugHistoryDto> findDrugHistoriesForPatient(Long patientId) throws HospitalException;
    List<DrugDto> findAllDrugs();
    DrugDto findById(Long drugId) throws HospitalException;
}
