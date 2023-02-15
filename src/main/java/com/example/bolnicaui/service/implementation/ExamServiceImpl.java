package com.example.bolnicaui.service.implementation;

import com.example.bolnicaui.domain.ChosenDoctor;
import com.example.bolnicaui.domain.Doctor;
import com.example.bolnicaui.domain.Nurse;
import com.example.bolnicaui.domain.Patient;
import com.example.bolnicaui.domain.exam.Exam;
import com.example.bolnicaui.dto.ExamDto;
import com.example.bolnicaui.exception.HospitalException;
import com.example.bolnicaui.exception.NoChosenDoctorException;
import com.example.bolnicaui.exception.NoSuchIdException;
import com.example.bolnicaui.mapper.ExamMapper;
import com.example.bolnicaui.repository.doctorRepo.DoctorRepository;
import com.example.bolnicaui.repository.doctorRepo.NurseRepository;
import com.example.bolnicaui.repository.examRepo.ExamRepository;
import com.example.bolnicaui.repository.examRepo.ExamTypeRepository;
import com.example.bolnicaui.repository.patientRepo.PatientRepository;
import com.example.bolnicaui.service.ExamService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {
    private final NurseRepository nurseRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ExamRepository examRepository;
    private final ExamTypeRepository examTypeRepository;
    private ExamMapper examMapper;

    public ExamServiceImpl(NurseRepository nurseRepository,
                           PatientRepository patientRepository,
                           DoctorRepository doctorRepository,
                           ExamRepository examRepository,
                           ExamTypeRepository examTypeRepository,
                           ExamMapper examMapper) {
        this.nurseRepository = nurseRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.examRepository = examRepository;
        this.examTypeRepository = examTypeRepository;
        this.examMapper = examMapper;
    }

    @Override
    public ExamDto addExamForPatient(Long patientId, Long examTypeId) throws HospitalException {
        Random random = new Random();

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new NoSuchIdException("There is no patient with Id = " + patientId));
        if(patient.getChosenDoctors().isEmpty()) {
            throw new NoChosenDoctorException("Patient with Id = " + patientId + " doesn't have primary doctor. Please choose a doctor first.");
        }
        Long currentChosenDoctorId = patient.getChosenDoctors().stream().filter(ChosenDoctor::isCurrently).toList().get(0).getId();
        if(patient.getChosenDoctors().stream().noneMatch(chosenDoctor -> chosenDoctor.getId().equals(currentChosenDoctorId))) {
            throw new NoChosenDoctorException("Patient with Id = " + patientId + " doesn't have primary doctor. Please choose a doctor first.");
        }

        List<Nurse> nursesWithLicences = nurseRepository.findAll().stream()
                .filter(Nurse::isWorkingLicence)
                .toList();
        Nurse randomNurse = nursesWithLicences.get(random.nextInt(0, nursesWithLicences.size()));
        List<Doctor> doctors = doctorRepository.findAll().stream()
                .filter(doctor -> doctor.getDoctorHolidays().stream()
                        .noneMatch(doctorHoliday -> doctorHoliday.getDateFrom().isBefore(LocalDate.now()) &&
                                   doctorHoliday.getDateTo().isAfter(LocalDate.now())))
                .toList(); //AAA VRACA DOKTORE KOJI NISU NA ODMORU AKO BOG DA
        Doctor randomDoctor = doctors.get(random.nextInt(0, doctors.size()));
        Exam exam = new Exam();
        patient.addExam(exam);
        randomDoctor.addExam(exam);
        randomNurse.addExam(exam);
        exam.setExamDate(LocalDate.now());
        exam.setExamType(examTypeRepository.findById(examTypeId).orElseThrow(() -> new NoSuchIdException("ExamType with Id="+examTypeId+" doesn't exist.")));
        examRepository.save(exam);
        patientRepository.save(patient);
        doctorRepository.save(randomDoctor);
        nurseRepository.save(randomNurse);
        return examMapper.mapToExamDto(exam);
    }

}
