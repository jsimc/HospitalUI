package com.example.bolnicaui.mapper;

import com.example.bolnicaui.domain.certificate.MedicalProcedure;
import com.example.bolnicaui.dto.MedicalProcedureDto;
import org.springframework.stereotype.Component;

@Component
public class MedicalProcedureMapper {
    public MedicalProcedureDto medicalProcedureToMedicalProcedureDto(MedicalProcedure medicalProcedure) {
        return new MedicalProcedureDto(medicalProcedure.getId(),
                medicalProcedure.getCode(),
                medicalProcedure.getName(),
                medicalProcedure.getPrice(),
                medicalProcedure.isCovidProcedure());
    }
}
