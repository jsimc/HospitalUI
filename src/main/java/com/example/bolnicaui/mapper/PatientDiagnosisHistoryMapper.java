package com.example.bolnicaui.mapper;

import com.example.bolnicaui.domain.diagnosis.PatientDiagnosisHistory;
import com.example.bolnicaui.dto.PatientDiagnosisHistoryDto;
import org.springframework.stereotype.Component;

@Component
public class PatientDiagnosisHistoryMapper {
    public PatientDiagnosisHistoryDto patientDiagnosisHistoryToPatientDiagnosisHistoryDto(PatientDiagnosisHistory patientDiagnosisHistory) {
        return new PatientDiagnosisHistoryDto(patientDiagnosisHistory.getId(),
                patientDiagnosisHistory.getDateFrom(),
                patientDiagnosisHistory.getDateTo(),
                patientDiagnosisHistory.isCurrentlyActive(),
                patientDiagnosisHistory.getPatient().getId(),
                patientDiagnosisHistory.getDiagnosis().getId());
    }
}
