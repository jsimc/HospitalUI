package com.example.bolnicaui.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptCreateDto {
    @NotBlank
    private boolean selfRenewing;
    @NotEmpty
    private LocalDate expireDate;
    @NotBlank
    private int dosage;
    @NotBlank
    private Long drugId;
    private Long examId;
}
