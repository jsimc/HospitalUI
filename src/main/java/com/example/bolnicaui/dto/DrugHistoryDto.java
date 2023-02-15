package com.example.bolnicaui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrugHistoryDto {
    private Long id;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Long drugId;
    private Long patientId;
}
