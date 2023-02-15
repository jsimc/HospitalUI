package com.example.bolnicaui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorOvertimeDto {
    private Long id;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Long doctor;
}
