package com.example.bolnicaui.mapper;

import com.example.bolnicaui.domain.drug.Drug;
import com.example.bolnicaui.domain.drug.DrugHistory;
import com.example.bolnicaui.domain.drug.Receipt;
import com.example.bolnicaui.dto.DrugDto;
import com.example.bolnicaui.dto.DrugHistoryDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DrugMapper {
    public DrugDto drugToDrugDto(Drug drug) {
        DrugDto drugDto = new DrugDto();
        drugDto.setId(drug.getId());
        drugDto.setDrug_name(drug.getDrug_name());
        try {
            drugDto.setDrugType(drug.getDrugType().getId());
            drugDto.setDrugHistories(drug.getDrugHistory().stream().map(DrugHistory::getId).collect(Collectors.toList()));
            drugDto.setPharmaCompany(drug.getPharmaCompany().getId());
            drugDto.setReceipts(drug.getReceipts().stream().map(Receipt::getId).collect(Collectors.toList()));
            drugDto.setGenericDrugName(drug.getGenericDrugName().getId());
        } catch (Exception ignored) {

        }
        return drugDto;
    }
    public DrugHistoryDto drugHistoryToDrugHistoryDto(DrugHistory drugHistory) {
        DrugHistoryDto drugHistoryDto = new DrugHistoryDto();
        return new DrugHistoryDto(drugHistoryDto.getId(), drugHistory.getDateFrom(), drugHistory.getDateTo(),
                drugHistory.getDrug().getId(), drugHistory.getPatient().getId());
    }
}
