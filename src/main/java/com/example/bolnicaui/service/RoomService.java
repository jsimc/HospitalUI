package com.example.bolnicaui.service;

import com.example.bolnicaui.dto.DepartmentDto;
import com.example.bolnicaui.dto.DoctorDto;
import com.example.bolnicaui.exception.HospitalException;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    Integer getNumberOfIntensiveCareRooms();
    DepartmentDto getMostProceduresDepartment();
    List<DoctorDto> getHeadDoctorsForDepartment(Long departmentId, LocalDate dateFrom, LocalDate dateTo) throws HospitalException;
}
