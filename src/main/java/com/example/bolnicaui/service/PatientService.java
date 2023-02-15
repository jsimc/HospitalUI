package com.example.bolnicaui.service;

import com.example.bolnicaui.domain.Patient;
import com.example.bolnicaui.domain.drug.Drug;
import com.example.bolnicaui.dto.HospitalStayDto;
import com.example.bolnicaui.dto.PatientCreateDto;
import com.example.bolnicaui.dto.PatientDiagnosisHistoryDto;
import com.example.bolnicaui.dto.PatientDto;
import com.example.bolnicaui.exception.HospitalException;
import com.example.bolnicaui.repository.drugRepo.PatientDrugI;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PatientService {
    PatientDto findPatientById(Long patientId) throws HospitalException;
    List<PatientDto> findAllPatients();
    PatientDto save(PatientCreateDto patientCreateDto);
    List<PatientDrugI> getMostCommonDrugPerPatient(Long diagnosisId);
    boolean removePatient(Long patientId) throws HospitalException;
    Float findPercentageOfPatientsWhoTookAntibioticsWhileCovid();
    PatientDto updatePatientPrimaryDoctor(Long patientId, Long doctorId) throws HospitalException;
    PatientDiagnosisHistoryDto addDiagnosisForPatient(Long patientId, Long diagnosisId) throws HospitalException;
    PatientDiagnosisHistoryDto setPatientDiagnosisNotActive(Long patientDiagnosisId) throws HospitalException;
    List<HospitalStayDto> getHospitalStaysForPatient(Long patientId) throws HospitalException;
}
