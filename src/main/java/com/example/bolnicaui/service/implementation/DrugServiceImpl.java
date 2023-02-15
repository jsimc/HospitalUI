package com.example.bolnicaui.service.implementation;

import com.example.bolnicaui.domain.Patient;
import com.example.bolnicaui.domain.diagnosis.Diagnosis;
import com.example.bolnicaui.domain.diagnosis.PatientDiagnosisHistory;
import com.example.bolnicaui.domain.drug.Drug;
import com.example.bolnicaui.domain.drug.DrugHistory;
import com.example.bolnicaui.domain.drug.PharmaCompany;
import com.example.bolnicaui.dto.*;
import com.example.bolnicaui.exception.HospitalException;
import com.example.bolnicaui.exception.NoSuchIdException;
import com.example.bolnicaui.mapper.DrugMapper;
import com.example.bolnicaui.mapper.PatientDiagnosisHistoryMapper;
import com.example.bolnicaui.mapper.PharmaCompanyMapper;
import com.example.bolnicaui.repository.diagnosisRepo.DiagnosisRepository;
import com.example.bolnicaui.repository.diagnosisRepo.PatientDiagnosisHistoryRepository;
import com.example.bolnicaui.repository.drugRepo.*;
import com.example.bolnicaui.repository.patientRepo.PatientRepository;
import com.example.bolnicaui.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DrugServiceImpl implements DrugService {
    private DrugRepository drugRepository;
    private DrugGenericNameRepository drugGenericNameRepository;
    private PharmaCompanyRepository pharmaCompanyRepository;
    private DrugHistoryRepo drugHistoryRepo;
    private DrugMapper drugMapper;
    private PharmaCompanyMapper pharmaCompanyMapper;
    private final PatientRepository patientRepository;

    public DrugServiceImpl(DrugRepository drugRepository, DrugMapper drugMapper, DrugGenericNameRepository drugGenericNameRepository,
                           PharmaCompanyRepository pharmaCompanyRepository, PharmaCompanyMapper pharmaCompanyMapper,
                           DrugHistoryRepo drugHistoryRepo,
                           PatientRepository patientRepository) {
        this.drugRepository = drugRepository;
        this.drugMapper = drugMapper;
        this.drugGenericNameRepository = drugGenericNameRepository;
        this.pharmaCompanyRepository = pharmaCompanyRepository;
        this.pharmaCompanyMapper = pharmaCompanyMapper;
        this.drugHistoryRepo = drugHistoryRepo;
        this.patientRepository = patientRepository;
    }
    @Override
    public PharmaCompanyDrugI findMostUsedDrugFromCompanyByGenericName(String drugGenericName) {
        Long genericDrugNameId = drugGenericNameRepository.findGenericDrugNameByName(drugGenericName)
                .getId();

        if(drugRepository.findMostUsedDrugFromCompany(genericDrugNameId).isEmpty()) {
            return null;
        }
        return drugRepository.findMostUsedDrugFromCompany(genericDrugNameId).get(0);
    }

    @Override
    public List<DrugHistoryDto> findDrugHistoriesForPatient(Long patientId) throws HospitalException {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if(patient != null) {
            return drugHistoryRepo.findDrugHistoriesByPatient(patient)
                    .stream().map(drugMapper::drugHistoryToDrugHistoryDto).collect(Collectors.toList());
        }
        else throw new NoSuchIdException("Ne postoji pacijent sa ID = " + patientId);
    }

    @Override
    public List<DrugDto> findAllDrugs() {
        return drugRepository.findAll().stream().map(drugMapper::drugToDrugDto).toList();
    }

    @Override
    public DrugDto findById(Long drugId) throws HospitalException {
        return drugMapper.drugToDrugDto(drugRepository.findById(drugId)
                .orElseThrow(()->new NoSuchIdException("No drug with Id = " + drugId)));
    }
}
