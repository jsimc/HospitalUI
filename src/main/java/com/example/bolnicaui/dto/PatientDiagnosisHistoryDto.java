package com.example.bolnicaui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDiagnosisHistoryDto {
    private Long id;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private boolean currentlyActive;
    private Long patientId;
    private Long diagnosisId;
}
