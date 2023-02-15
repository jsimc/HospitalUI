package com.example.bolnicaui.dto;

import com.example.bolnicaui.domain.Adress;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientCreateDto {
    private String firstName;
    private String lastName;
    private String JMBG;
    @Null
    private Long adress;
    private String phoneNumber;
}
