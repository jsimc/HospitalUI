package com.example.bolnicaui.mapper;

import com.example.bolnicaui.domain.drug.Drug;
import com.example.bolnicaui.domain.drug.PharmaCompany;
import com.example.bolnicaui.dto.PharmaCompanyDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class PharmaCompanyMapper {
    public PharmaCompanyDto pharmaCompanyToPharmaCompanyDto(PharmaCompany pharmaCompany) {
        PharmaCompanyDto pharmaCompanyDto = new PharmaCompanyDto();
        pharmaCompanyDto.setId(pharmaCompany.getId());
        pharmaCompanyDto.setName(pharmaCompany.getName());
        pharmaCompanyDto.setDrugs(pharmaCompany.getDrugs().stream().map(Drug::getId).collect(Collectors.toList()));
        return pharmaCompanyDto;
    }
}
