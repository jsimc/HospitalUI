package com.example.bolnicaui.service.implementation;

import com.example.bolnicaui.domain.ChosenDoctor;
import com.example.bolnicaui.domain.CovidReport;
import com.example.bolnicaui.domain.Doctor;
import com.example.bolnicaui.domain.Patient;
import com.example.bolnicaui.domain.diagnosis.Diagnosis;
import com.example.bolnicaui.domain.diagnosis.PatientDiagnosisHistory;
import com.example.bolnicaui.dto.HospitalStayDto;
import com.example.bolnicaui.dto.PatientCreateDto;
import com.example.bolnicaui.dto.PatientDiagnosisHistoryDto;
import com.example.bolnicaui.dto.PatientDto;
import com.example.bolnicaui.exception.HospitalException;
import com.example.bolnicaui.exception.NoSuchIdException;
import com.example.bolnicaui.mapper.HospitalStayMapper;
import com.example.bolnicaui.mapper.PatientDiagnosisHistoryMapper;
import com.example.bolnicaui.mapper.PatientMapper;
import com.example.bolnicaui.repository.diagnosisRepo.DiagnosisRepository;
import com.example.bolnicaui.repository.diagnosisRepo.PatientDiagnosisHistoryRepository;
import com.example.bolnicaui.repository.doctorRepo.ChosenDoctorRepository;
import com.example.bolnicaui.repository.doctorRepo.DoctorRepository;
import com.example.bolnicaui.repository.drugRepo.DrugRepository;
import com.example.bolnicaui.repository.drugRepo.PatientDrugI;
import com.example.bolnicaui.repository.patientRepo.CovidReportRepository;
import com.example.bolnicaui.repository.patientRepo.PatientRepository;
import com.example.bolnicaui.service.PatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;
    private DrugRepository drugRepository;
    private PatientMapper patientMapper;
    private DoctorRepository doctorRepository;
    private ChosenDoctorRepository chosenDoctorRepository;
    private DiagnosisRepository diagnosisRepository;
    private PatientDiagnosisHistoryRepository patientDiagnosisHistoryRepository;
    private PatientDiagnosisHistoryMapper patientDiagnosisMapper;
    private HospitalStayMapper hospitalStayMapper;
    private final CovidReportRepository covidReportRepository;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper, DrugRepository drugRepository,
                              DoctorRepository doctorRepository,
                              ChosenDoctorRepository chosenDoctorRepository,
                              DiagnosisRepository diagnosisRepository,
                              PatientDiagnosisHistoryRepository patientDiagnosisHistoryRepository,
                              PatientDiagnosisHistoryMapper patientDiagnosisMapper,
                              HospitalStayMapper hospitalStayMapper,
                              CovidReportRepository covidReportRepository) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.drugRepository = drugRepository;
        this.doctorRepository = doctorRepository;
        this.chosenDoctorRepository = chosenDoctorRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.patientDiagnosisHistoryRepository = patientDiagnosisHistoryRepository;
        this.patientDiagnosisMapper = patientDiagnosisMapper;
        this.hospitalStayMapper = hospitalStayMapper;
        this.covidReportRepository = covidReportRepository;
    }

    @Override
    public PatientDto findPatientById(Long patientId) throws HospitalException{
        return patientMapper.patientToPatientDto(patientRepository.findById(patientId)
                .orElseThrow(() -> new NoSuchIdException("There is no patient with id = " + patientId)));
    }

    @Override
    public List<PatientDto> findAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patient -> patientMapper.patientToPatientDto(patient))
                .collect(Collectors.toList());
    }

    @Override
    public PatientDto save(PatientCreateDto patientCreateDto) {
        return patientMapper.patientToPatientDto(
                patientRepository.save(patientMapper.patientCreateDtoToPatient(patientCreateDto))
        );
    }

    @Override
    public List<PatientDrugI> getMostCommonDrugPerPatient(Long diagnosisId) {
        return drugRepository.findMostCommonDrugPerPatientForGivenDiagnosis(diagnosisId);
    }

    @Override
    public boolean removePatient(Long patientId) throws HospitalException {
        patientRepository.findById(patientId).orElseThrow(() -> new NoSuchIdException("There is no patient with Id = " + patientId));
        patientRepository.deleteById(patientId);
        return true;
    }

    @Override
    public Float findPercentageOfPatientsWhoTookAntibioticsWhileCovid() {
        int numberOfPeopleWhoTookAntibioticsWhileHadCovid = patientRepository.prviQuery();
        int totalNumberCovidPatientsHistory = patientRepository.drugiQuery();
        return 100.00f*numberOfPeopleWhoTookAntibioticsWhileHadCovid/totalNumberCovidPatientsHistory;
    }

    @Override
    public PatientDto updatePatientPrimaryDoctor(Long patientId, Long doctorId) throws HospitalException{
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new NoSuchIdException("There is no patient with id: " + patientId));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new NoSuchIdException("There is no doctor with id: " + doctorId));
        ChosenDoctor chosenDoctor = new ChosenDoctor();
        chosenDoctorRepository.save(chosenDoctor);
        patient.addChosenDoctor(chosenDoctor);
        doctor.addChosenDoctor(chosenDoctor);
        patientRepository.save(patient);
        doctorRepository.save(doctor);
        chosenDoctorRepository.updateChosenDoctor(chosenDoctor.isCurrently(), chosenDoctor.getFromDate(), null,
                chosenDoctor.getDoctor().getId(), chosenDoctor.getPatient().getId(), chosenDoctor.getId());

        return patientMapper.patientToPatientDto(patient);
    }
    @Override
    public PatientDiagnosisHistoryDto addDiagnosisForPatient(Long patientId, Long diagnosisId) throws HospitalException {
        PatientDiagnosisHistory patientDiagnosisHistory = new PatientDiagnosisHistory();
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId).orElseThrow(() -> new NoSuchIdException("There is no diagnosis with id = " + diagnosisId));
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new NoSuchIdException("There is no patient with ID = " + patientId));
        ///////////////////////// PROVERA ZA KOVID ///////////////////////////
        if (diagnosis.getId() == 5L || diagnosis.getName().equals("kovid-19")) {
            CovidReport covidReport = new CovidReport();
            patient.addCovidReport(covidReport);
            covidReportRepository.save(covidReport);
        }
        //////////////////////////////////////////////////////////////////////
        diagnosis.addPatientDiagnosisHistory(patientDiagnosisHistory);
        patient.addPatientDiagnosisHistory(patientDiagnosisHistory);
        patientDiagnosisHistory.setCurrentlyActive(true);
        patientDiagnosisHistory.setDateFrom(LocalDate.now());
        patientDiagnosisHistoryRepository.save(patientDiagnosisHistory);
        return patientDiagnosisMapper.patientDiagnosisHistoryToPatientDiagnosisHistoryDto(patientDiagnosisHistory);
    }

    @Override
    public PatientDiagnosisHistoryDto setPatientDiagnosisNotActive(Long patientDiagnosisId) throws HospitalException{
        PatientDiagnosisHistory patientDiagnosisHistory = patientDiagnosisHistoryRepository
                .findById(patientDiagnosisId)
                .orElseThrow(() -> new NoSuchIdException("There is no PatientDiagnosis with Id = " + patientDiagnosisId));
        /////////////////////////// PROVERA ZA KOVID ///////////////////////////////
        if(patientDiagnosisHistory.getDiagnosis().getId() == 5L ||
            patientDiagnosisHistory.getDiagnosis().getName().equals("kovid-19")){
            CovidReport covidReport = covidReportRepository.findCovidReportByPatientAndCurrentlySickIsTrue(patientDiagnosisHistory.getPatient());
            covidReport.setDateTo(LocalDate.now());
            covidReport.setCurrentlySick(false);
            covidReportRepository.save(covidReport);
        }
        ///////////////////////////////////////////////////////////////////////////
        patientDiagnosisHistory.setDateTo(LocalDate.now());
        patientDiagnosisHistory.setCurrentlyActive(false);
        patientDiagnosisHistoryRepository.updateIsActiveAndDateTo(patientDiagnosisId, patientDiagnosisHistory.getDateTo());
        return patientDiagnosisMapper.patientDiagnosisHistoryToPatientDiagnosisHistoryDto(patientDiagnosisHistory);
    }

    @Override
    public List<HospitalStayDto> getHospitalStaysForPatient(Long patientId) throws HospitalException {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()->new NoSuchIdException("No patient with Id = " + patientId));
        return patient.getHospitalStays().stream().map(hospitalStayMapper::mapToHospitalStayDto).toList();
    }
}
