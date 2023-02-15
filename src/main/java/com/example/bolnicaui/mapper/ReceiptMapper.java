package com.example.bolnicaui.mapper;

import com.example.bolnicaui.domain.drug.Receipt;
import com.example.bolnicaui.dto.ReceiptDto;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class ReceiptMapper {
    public ReceiptDto mapToReceiptDto(Receipt receipt) {
        return new ReceiptDto(receipt.getId(),
                receipt.isSelfRenewing(),
                receipt.getIssueDate(),
                receipt.getExpireDate(),
                receipt.getDosage(),
                receipt.getDoctor().getId(),
                receipt.getPatient().getId(),
                receipt.getDrug().getId(),
                Optional.of(receipt.getExam().getId()).orElse(null));
    }
}
