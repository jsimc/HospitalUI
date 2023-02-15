package com.example.bolnicaui.dto;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorCreateDto {
    private String firstName;
    private String lastName;
    private long freeTimeDays;
    @Null
    private Long specializationType;
}
