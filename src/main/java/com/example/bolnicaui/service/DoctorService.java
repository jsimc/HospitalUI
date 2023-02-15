package com.example.bolnicaui.service;

import com.example.bolnicaui.domain.rooms.HospitalStay;
import com.example.bolnicaui.dto.*;
import com.example.bolnicaui.exception.HospitalException;
import com.example.bolnicaui.exception.NoSuchIdException;
import com.example.bolnicaui.repository.doctorRepo.DoctorFreeDaysLeftI;
import com.example.bolnicaui.repository.doctorRepo.DoctorNameAvgProcedurePerWeekI;
import com.example.bolnicaui.repository.doctorRepo.NurseFreeDaysLeftI;
import com.example.bolnicaui.repository.doctorRepo.SpecializationResultI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DoctorService {
    List<DoctorDto> getAllDoctors();
    List<SpecializationResultI> findAverageNumberOfCertificatesPerSpecialization();
    List<DoctorNameAvgProcedurePerWeekI> findAverageNumberOfProceduresPerWeek();
    List<MedicalProcedureDto> getAllProcedures();
    List<DoctorFreeDaysLeftI> getDoctorFreeDaysLeft();
    List<NurseFreeDaysLeftI> getNurseFreeDaysLeft();
    ReceiptDto addReceiptToThePatient(Long doctorId, Long patientId, ReceiptCreateDto receiptCreateDto) throws HospitalException;
    ReceiptDto stopRenewingTheReceipt(Long receiptId) throws HospitalException;
    HospitalStayDto putPatientInHospitalStay(Long patientId, Long roomId) throws HospitalException;
    DischargeDto dischargePatient(Long doctorId, Long hospitalStayId) throws HospitalException;
    DoctorDto getDoctorById(Long doctorId) throws HospitalException;
    DoctorOvertimeDto putDoctorForOvertime(Long doctorId, LocalDateTime dateFrom, LocalDateTime dateTo) throws HospitalException;
    DoctorDto saveDoctor(DoctorCreateDto doctorCreateDto);
}
