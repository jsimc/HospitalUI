package com.example.bolnicaui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DischargeDto {
    private Long id;
    private Long doctor;
    private Long hospitalStay;
    private Long bill;
}
