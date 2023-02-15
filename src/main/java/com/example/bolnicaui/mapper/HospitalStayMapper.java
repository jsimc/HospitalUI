package com.example.bolnicaui.mapper;

import com.example.bolnicaui.domain.rooms.HospitalStay;
import com.example.bolnicaui.dto.HospitalStayDto;
import org.springframework.stereotype.Component;
import static com.example.bolnicaui.exception.DoNothingException.*;


@Component
public class HospitalStayMapper {
    public HospitalStayDto mapToHospitalStayDto(HospitalStay hospitalStay) {
        HospitalStayDto hospitalStayDto = new HospitalStayDto();
        hospitalStayDto.setId(hospitalStay.getId());
        hospitalStayDto.setDateFrom(hospitalStay.getDateFrom());
        hospitalStayDto.setDateTo(hospitalStay.getDateTo());
        hospitalStayDto.setCurrentlyActive(hospitalStay.isCurrentlyActive());
        hospitalStayDto.setPatient(hospitalStay.getPatient().getId());
        hospitalStayDto.setRoom(hospitalStay.getRoom().getId());
        ignoringExc(()->hospitalStayDto.setDischarge(hospitalStay.getDischarge().getId()));
        return hospitalStayDto;
    }
}
