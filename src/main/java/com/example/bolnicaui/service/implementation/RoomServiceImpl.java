package com.example.bolnicaui.service.implementation;

import com.example.bolnicaui.domain.rooms.Department;
import com.example.bolnicaui.domain.rooms.DoctorDepartment;
import com.example.bolnicaui.dto.DepartmentDto;
import com.example.bolnicaui.dto.DoctorDto;
import com.example.bolnicaui.exception.HospitalException;
import com.example.bolnicaui.exception.NoSuchIdException;
import com.example.bolnicaui.mapper.DepartmentMapper;
import com.example.bolnicaui.mapper.DoctorMapper;
import com.example.bolnicaui.repository.roomRepo.DepartmentRepository;
import com.example.bolnicaui.repository.roomRepo.RoomRepository;
import com.example.bolnicaui.service.RoomService;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;
    private DepartmentRepository departmentRepository;
    private DepartmentMapper departmentMapper;
    private DoctorMapper doctorMapper;

    public RoomServiceImpl(RoomRepository roomRepository,
                           DepartmentRepository departmentRepository,
                           DoctorMapper doctorMapper,
                           DepartmentMapper departmentMapper) {
        this.roomRepository = roomRepository;
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.doctorMapper = doctorMapper;
    }

    @Override
    public Integer getNumberOfIntensiveCareRooms() {
        return roomRepository.findNumberOfIntensiveCareRooms();
    }

    @Override
    public DepartmentDto getMostProceduresDepartment() {
        return departmentMapper.departmentToDepartmentDto(departmentRepository.getMostProceduresDepartment().get(0));
    }

    @Override
    public List<DoctorDto> getHeadDoctorsForDepartment(Long departmentId, LocalDate dateFrom, LocalDate dateTo) throws HospitalException {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(()->new NoSuchIdException("No department with Id = " + departmentId));
        return department.getDoctorDepartments().stream()
                .filter(DoctorDepartment::isHeadDoctor)
                .filter(doctorDepartment -> {
                    if(dateFrom == null || dateTo == null) {
                        return true;
                    }
                    return dateFrom.isBefore(doctorDepartment.getDateFrom()) && dateTo.isAfter(doctorDepartment.getDateFrom());
                })
                .map(DoctorDepartment::getDoctor)
                .map(doctor -> doctorMapper.doctorToDoctorDto(doctor))
                .toList();
    }

}
