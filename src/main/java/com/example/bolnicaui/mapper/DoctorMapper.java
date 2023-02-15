package com.example.bolnicaui.mapper;

import com.example.bolnicaui.domain.ChosenDoctor;
import com.example.bolnicaui.domain.CovidReport;
import com.example.bolnicaui.domain.Doctor;
import com.example.bolnicaui.domain.certificate.DoctorCertificate;
import com.example.bolnicaui.domain.certificate.PerformedProcedure;
import com.example.bolnicaui.domain.drug.Receipt;
import com.example.bolnicaui.domain.duty.DutyDoctor;
import com.example.bolnicaui.domain.exam.Exam;
import com.example.bolnicaui.domain.overtime.DoctorOvertime;
import com.example.bolnicaui.domain.rooms.Discharge;
import com.example.bolnicaui.domain.rooms.DoctorDepartment;
import com.example.bolnicaui.domain.vacation.DoctorHoliday;
import com.example.bolnicaui.dto.DoctorCreateDto;
import com.example.bolnicaui.dto.DoctorDto;
import com.example.bolnicaui.repository.doctorRepo.SpecializationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.bolnicaui.exception.DoNothingException.ignoringExc;

@Component
public class DoctorMapper {
    @Autowired
    private SpecializationTypeRepository specializationTypeRepository;

    public DoctorDto doctorToDoctorDto(Doctor doctor) {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setId(doctor.getId());
        doctorDto.setFirstName(doctor.getFirstName());
        doctorDto.setLastName(doctor.getLastName());
        ignoringExc(()->doctorDto.setFreeTimeDays(doctor.getFreeTimeDays()));
        /////////////////////////////////////////////////////
        ignoringExc(()->doctorDto.setSpecializationType(doctor.getSpecializationType().getId()));
        ignoringExc(()->doctorDto.setExams(doctor.getExams().stream().map(Exam::getId).toList()));
        ignoringExc(()->doctorDto.setReceipts(doctor.getReceipts().stream().map(Receipt::getId).toList()));
        ignoringExc(()->doctorDto.setChosenDoctors(doctor.getChosenDoctors().stream().map(ChosenDoctor::getId).toList()));
        ignoringExc(()->doctorDto.setDoctorDepartments(doctor.getDoctorDepartments().stream().map(DoctorDepartment::getId).toList()));
        ignoringExc(()->doctorDto.setDoctorCertificates(doctor.getDoctorCertificates().stream().map(DoctorCertificate::getId).toList()));
        ignoringExc(()->doctorDto.setPerformedProcedures(doctor.getPerformedProcedures().stream().map(PerformedProcedure::getId).toList()));
        ignoringExc(()->doctorDto.setCovidReports(doctor.getCovidReports().stream().map(CovidReport::getId).toList()));
        ignoringExc(()->doctorDto.setDutyDoctors(doctor.getDutyDoctors().stream().map(DutyDoctor::getId).toList()));
        ignoringExc(()->doctorDto.setDoctorOvertimes(doctor.getDoctorOvertimes().stream().map(DoctorOvertime::getId).toList()));
        ignoringExc(()->doctorDto.setDoctorHolidays(doctor.getDoctorHolidays().stream().map(DoctorHoliday::getId).toList()));
        ignoringExc(()->doctorDto.setDischarges(doctor.getDischarges().stream().map(Discharge::getId).toList()));
        return doctorDto;
    }
    public Doctor doctorCreateDtoToDoctor(DoctorCreateDto doctorCreateDto) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorCreateDto.getFirstName());
        doctor.setLastName(doctorCreateDto.getLastName());
        doctor.setFreeTimeDays(doctorCreateDto.getFreeTimeDays());
        ignoringExc(()->doctor.setSpecializationType(
                specializationTypeRepository.findById(doctorCreateDto.getSpecializationType()).orElse(null)));
        return doctor;
    }
}
