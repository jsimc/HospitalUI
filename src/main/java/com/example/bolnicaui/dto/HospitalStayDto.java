package com.example.bolnicaui.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalStayDto {
    private Long id;
    @NotNull
    private LocalDate dateFrom;
    private LocalDate dateTo;
    @NotNull
    private boolean currentlyActive;
    @NotNull
    private Long patient;
    @NotNull
    private Long room;
    private Long discharge;
}
