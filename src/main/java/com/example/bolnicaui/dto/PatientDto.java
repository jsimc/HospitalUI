package com.example.bolnicaui.dto;

import com.example.bolnicaui.domain.Adress;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    private long id;
    private String firstName;
    private String lastName;
    private String JMBG;
    private String phoneNumber;
    @Nullable
    private Long adress;
    @Nullable
    private List<Long> drugHistories;
    @Nullable
    private List<Long> patientDiagnosisHistories;
    @Nullable
    private List<Long> covidReports;
    @Nullable
    private List<Long> patientInsurances;
    @Nullable
    private List<Long> exams;
    @Nullable
    private List<Long> receipts;
    @Nullable
    private List<Long> chosenDoctors;
    @Nullable
    private Long chosenDoctor;
    @Nullable
    private List<Long> hospitalStays;
    @Nullable
    private List<Long> performedProcedures;
}
