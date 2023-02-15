package com.example.bolnicaui.dto;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDto {
    private Long id;
    private boolean selfRenewing;
    private LocalDate issueDate;
    private LocalDate expireDate;
    private int dosage; //TODO double dosage?
    private Long doctor;
    private Long patient;
    private Long drug;
    @Null
    private Long exam;
}
