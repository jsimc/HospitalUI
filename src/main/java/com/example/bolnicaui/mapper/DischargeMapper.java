package com.example.bolnicaui.mapper;

import com.example.bolnicaui.domain.rooms.Discharge;
import com.example.bolnicaui.dto.DischargeDto;
import org.springframework.stereotype.Component;

@Component
public class DischargeMapper {
    public DischargeDto mapToDischargeDto(Discharge discharge) {
        return new DischargeDto(discharge.getId(),
                discharge.getDoctor().getId(),
                discharge.getHospitalStay().getId(),
                discharge.getBill().getId());
    }
}
