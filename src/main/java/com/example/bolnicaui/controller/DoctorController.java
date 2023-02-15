package com.example.bolnicaui.controller;

import com.example.bolnicaui.dto.*;
import com.example.bolnicaui.exception.HospitalException;
import com.example.bolnicaui.repository.doctorRepo.DoctorFreeDaysLeftI;
import com.example.bolnicaui.repository.doctorRepo.DoctorNameAvgProcedurePerWeekI;
import com.example.bolnicaui.repository.doctorRepo.NurseFreeDaysLeftI;
import com.example.bolnicaui.repository.doctorRepo.SpecializationResultI;
import com.example.bolnicaui.service.DoctorService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private DoctorService doctorService;
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long doctorId) {
        try {
            return new ResponseEntity<>(doctorService.getDoctorById(doctorId), HttpStatus.OK);
        } catch (HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping()
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
    }
    @PostMapping("/saveDoctor")
    public ResponseEntity<DoctorDto> saveDoctor(@RequestBody @Valid DoctorCreateDto doctorCreateDto) {
        return new ResponseEntity<>(doctorService.saveDoctor(doctorCreateDto), HttpStatus.CREATED);
    }
    @GetMapping("/findAverageNumberOfCertificatesPerSpecialization")
    public ResponseEntity<List<SpecializationResultI>> findAverageNumberOfCertificatesPerSpecialization() {
        return new ResponseEntity<>(doctorService.findAverageNumberOfCertificatesPerSpecialization(), HttpStatus.OK);
    }
    @GetMapping("/findAverageNumberOfProceduresPerWeek")
    public ResponseEntity<List<DoctorNameAvgProcedurePerWeekI>> findAverageNumberOfProceduresPerWeek() {
        return new ResponseEntity<>(doctorService.findAverageNumberOfProceduresPerWeek(), HttpStatus.OK);
    }
    @GetMapping("/getAllProcedures")
    public ResponseEntity<List<MedicalProcedureDto>> getAllProcedures(){
        return new ResponseEntity<>(doctorService.getAllProcedures(), HttpStatus.OK);
    }
    @GetMapping("/getFreeDaysLeftDoctors")
    public ResponseEntity<List<DoctorFreeDaysLeftI>> getDoctorFreeDaysLeft() {
        return new ResponseEntity<>(doctorService.getDoctorFreeDaysLeft(), HttpStatus.OK);
    }
    @GetMapping("/getFreeDaysLeftNurses")
    public ResponseEntity<List<NurseFreeDaysLeftI>> getNurseFreeDaysLeft() {
        return new ResponseEntity<>(doctorService.getNurseFreeDaysLeft(), HttpStatus.OK);
    }
    @PostMapping("/{doctorId}/addReceiptToThePatient")
    public ResponseEntity<ReceiptDto> addReceiptToThePatient(@PathVariable Long doctorId,
                                                             @RequestParam(name = "patientId") Long patientId,
                                                             @RequestBody ReceiptCreateDto receiptCreateDto) {
        try {
            return new ResponseEntity<>(doctorService.addReceiptToThePatient(doctorId, patientId, receiptCreateDto), HttpStatus.CREATED);
        } catch (HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/stopRenewingTheReceipt/{receiptId}")
    public ResponseEntity<ReceiptDto> stopRenewingTheReceipt(@PathVariable Long receiptId) {
        try {
            return new ResponseEntity<>(doctorService.stopRenewingTheReceipt(receiptId), HttpStatus.ACCEPTED);
        } catch(HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    // Hospital Stay nema informacija o doktoru, i nema informacija da li je pacijent bio prethodno na nekom pregledu
    // Tako da roomId se unosi ~rucno~ nadajuci se da ce ga doktor staviti u sobu koja je za pravo odeljenje
    // A pritom ne bi bas bilo dobro da program ZABRANI doktoru da stavi pacijenta u bilo koju sobu
    // (Npr. slucaj prenatrpanosti, ili hitan slucaj za pacijenta...)
    @PostMapping("/putPatientInHospitalStay")
    public ResponseEntity<?> putPatientInHospitalStay(@RequestParam(name = "patientId") Long patientId,
                                                      @RequestParam(name = "roomId") Long roomId) {
        try {
            return new ResponseEntity<>(doctorService.putPatientInHospitalStay(patientId, roomId), HttpStatus.CREATED);
        } catch(HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/{doctorId}/dischargePatient")
    public ResponseEntity<?> dischargePatient(@PathVariable Long doctorId,
                                              @RequestParam(name = "hospitalStayId") Long hospitalStayId) {
        try {
            return new ResponseEntity<>(doctorService.dischargePatient(doctorId, hospitalStayId), HttpStatus.CREATED);
        } catch(HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{doctorId}/putDoctorForOvertime")
    public ResponseEntity<DoctorOvertimeDto> putDoctorForOvertime(@PathVariable Long doctorId,
    @RequestParam(name = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-mm-dd HH:mm") LocalDateTime dateFrom,
    @RequestParam(name = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-mm-dd HH:mm") LocalDateTime dateTo) {
        try{
            return new ResponseEntity<>(doctorService.putDoctorForOvertime(doctorId, dateFrom, dateTo), HttpStatus.CREATED);
        } catch (HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
