package com.example.bolnicaui.mapper;

import com.example.bolnicaui.domain.Adress;
import com.example.bolnicaui.domain.ChosenDoctor;
import com.example.bolnicaui.domain.CovidReport;
import com.example.bolnicaui.domain.Patient;
import com.example.bolnicaui.domain.certificate.PerformedProcedure;
import com.example.bolnicaui.domain.diagnosis.PatientDiagnosisHistory;
import com.example.bolnicaui.domain.drug.DrugHistory;
import com.example.bolnicaui.domain.drug.Receipt;
import com.example.bolnicaui.domain.exam.Exam;
import com.example.bolnicaui.domain.insurance.PatientInsurance;
import com.example.bolnicaui.domain.rooms.HospitalStay;
import com.example.bolnicaui.dto.PatientCreateDto;
import com.example.bolnicaui.dto.PatientDto;
import com.example.bolnicaui.repository.doctorRepo.DoctorRepository;
import com.example.bolnicaui.repository.patientRepo.AdressRepository;
import com.example.bolnicaui.repository.patientRepo.PatientRepository;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;
import static com.example.bolnicaui.exception.DoNothingException.*;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PatientMapper {
    PatientRepository patientRepository;
    AdressRepository adressRepository;
    private DoctorRepository doctorRepository;

    public PatientMapper(PatientRepository patientRepository, AdressRepository adressRepository,
                         DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.adressRepository = adressRepository;
        this.doctorRepository = doctorRepository;
    }

    public PatientDto patientToPatientDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(patient.getId());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setJMBG(patient.getJMBG());
        ignoringExc(() -> patientDto.setPhoneNumber(patient.getPhoneNumber()));
        ignoringExc(() -> patientDto.setAdress(patient.getAdress().getId()));
        ignoringExc(() -> patientDto.setChosenDoctor(doctorRepository.findById(patient
                .getChosenDoctors().stream().filter(ChosenDoctor::isCurrently).toList().get(0).getId()).get().getId()));

        ignoringExc(() -> patientDto.setPatientInsurances(patient.getPatientInsurances().stream().map(PatientInsurance::getId).collect(Collectors.toList())));
        ignoringExc(() -> patientDto.setPatientDiagnosisHistories(patient.getPatientDiagnosisHistories().stream().map(PatientDiagnosisHistory::getId).collect(Collectors.toList())));
        ignoringExc(() -> patientDto.setExams(patient.getExams().stream().map(Exam::getId).collect(Collectors.toList())));
        ignoringExc(() -> patientDto.setChosenDoctors(patient.getChosenDoctors().stream().map(ChosenDoctor::getId).collect(Collectors.toList())));
        ignoringExc(() -> patientDto.setReceipts(patient.getReceipts().stream().map(Receipt::getId).collect(Collectors.toList())));
        ignoringExc(() -> patientDto.setCovidReports(patient.getCovidReports().stream().map(CovidReport::getId).collect(Collectors.toList())));
        ignoringExc(() -> patientDto.setDrugHistories(patient.getDrugHistories().stream().map(DrugHistory::getId).collect(Collectors.toList())));
        ignoringExc(() -> patientDto.setHospitalStays(patient.getHospitalStays().stream().map(HospitalStay::getId).collect(Collectors.toList())));
        ignoringExc(() -> patientDto.setPerformedProcedures(patient.getPerformedProcedures().stream().map(PerformedProcedure::getId).collect(Collectors.toList())));
        return patientDto;
    }

    public Patient patientCreateDtoToPatient(PatientCreateDto patientCreateDto) {
        Patient patient = new Patient();
        ignoringExc(()->patient.setAdress(adressRepository.findById(patientCreateDto.getAdress()).orElse(null)));
        patient.setFirstName(patientCreateDto.getFirstName());
        patient.setLastName(patientCreateDto.getLastName());
        ignoringExc(() -> patient.setJMBG(patientCreateDto.getJMBG()));
        ignoringExc(() -> patient.setPhoneNumber(patientCreateDto.getPhoneNumber()));
        return patient;
    }
}
