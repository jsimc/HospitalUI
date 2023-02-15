package com.example.bolnicaui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalProcedureDto {
    private Long id;
    private String code;
    private String name;
    private Double price;
    private boolean covidProcedure;

}
