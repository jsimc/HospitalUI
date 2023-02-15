package com.example.bolnicaui.dto;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto {
    private Long id;
    private LocalDate examDate;
    private Long doctor;
    private Long patient;
    private Long nurse;
    private Long examType;
    private List<Long> receipts;
}
