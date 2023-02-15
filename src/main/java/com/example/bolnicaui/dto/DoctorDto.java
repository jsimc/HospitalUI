package com.example.bolnicaui.dto;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private long freeTimeDays;
    @Null
    private Long specializationType;
    private List<Long> exams;
    private List<Long> receipts;
    private List<Long> chosenDoctors;
    private List<Long> doctorDepartments;
    private List<Long> doctorCertificates;
    private List<Long> performedProcedures;
    private List<Long> covidReports;
    private List<Long> dutyDoctors;
    private List<Long> doctorOvertimes;
    private List<Long> doctorHolidays;
    private List<Long> discharges;
}
