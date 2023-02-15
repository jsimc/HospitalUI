package com.example.bolnicaui.dto;

import com.example.bolnicaui.domain.drug.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrugDto {
    private Long id;
    private String drug_name;
    private Long pharmaCompany;
    private Long genericDrugName;
    private Long drugType;
    private List<Long> drugHistories;
    private List<Long> receipts;
}
