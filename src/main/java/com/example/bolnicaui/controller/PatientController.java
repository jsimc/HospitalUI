package com.example.bolnicaui.controller;

import com.example.bolnicaui.dto.PatientCreateDto;
import com.example.bolnicaui.dto.PatientDiagnosisHistoryDto;
import com.example.bolnicaui.dto.PatientDto;
import com.example.bolnicaui.exception.HospitalException;
import com.example.bolnicaui.repository.drugRepo.PatientDrugI;
import com.example.bolnicaui.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private PatientService patientService;
    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long patientId) {
        try {
            return new ResponseEntity<>(patientService.findPatientById(patientId), HttpStatus.OK);
        } catch (HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return new ResponseEntity<>(patientService.findAllPatients(), HttpStatus.OK);
    }

    @PostMapping("/savePatient")
    public ResponseEntity<PatientDto> savePatient(@RequestBody @Valid PatientCreateDto patientCreateDto) {
        return new ResponseEntity<>(patientService.save(patientCreateDto), HttpStatus.OK);
    }
    @PutMapping("/updatePatientPrimaryDoctor/{patientId}")
    public ResponseEntity<PatientDto> updatePatientPrimaryDoctor(@PathVariable Long patientId, @RequestParam(name = "doctorId", required = true) Long doctorId) {
        try {
            return new ResponseEntity<>(patientService.updatePatientPrimaryDoctor(patientId, doctorId), HttpStatus.ACCEPTED);
        } catch (HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getMostCommonDrugPerPatient/{diagnosisId}")
    public ResponseEntity<List<PatientDrugI>> mostCommonDrugPerPatient(@PathVariable Long diagnosisId) {
        return new ResponseEntity<>(patientService.getMostCommonDrugPerPatient(diagnosisId), HttpStatus.OK);
    }
    @GetMapping("/percentageOfPatientsWhoTookAntibioticsWhileHadCovid")
    public ResponseEntity<Float> percentageOfPatientsWhoTookAntibioticsWhileHadCovid() {
        return new ResponseEntity<>(patientService.findPercentageOfPatientsWhoTookAntibioticsWhileCovid(), HttpStatus.OK);
    }

    @DeleteMapping("/deletePatient/{patientId}")
    public ResponseEntity<?> removePatient(@PathVariable Long patientId) {
        try {
            boolean removed = patientService.removePatient(patientId);
            if(removed){
                return ResponseEntity.ok().body("Removed patient with id " + patientId);
            }
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during removal of patient with id " + patientId);
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/addDiagnosisForPatient")
    public ResponseEntity<PatientDiagnosisHistoryDto> addDiagnosisForPatient(@RequestParam(name = "patientId") Long patientId, @RequestParam(name = "diagnosisId") Long diagnosisId) {
        try {
            return new ResponseEntity<>(patientService.addDiagnosisForPatient(patientId, diagnosisId), HttpStatus.CREATED);
        } catch (HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/setPatientDiagnosisNotActive")
    public ResponseEntity<PatientDiagnosisHistoryDto> setPatientDiagnosisNotActive(@RequestParam(name = "patientDiagnosisId") Long patientDiagnosisId) {
        try {
            return new ResponseEntity<>(patientService.setPatientDiagnosisNotActive(patientDiagnosisId), HttpStatus.OK);
        } catch (HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{patientId}/getHospitalStay")
    public ResponseEntity<?> getHospitalStaysForPatient(@PathVariable Long patientId) {
        try {
            return new ResponseEntity<>(patientService.getHospitalStaysForPatient(patientId), HttpStatus.OK);
        } catch (HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
