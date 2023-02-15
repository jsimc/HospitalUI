package com.example.bolnicaui.controller;

import com.example.bolnicaui.dto.DrugDto;
import com.example.bolnicaui.dto.DrugHistoryDto;
import com.example.bolnicaui.exception.HospitalException;
import com.example.bolnicaui.service.DrugService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drug")
public class DrugController {
    private DrugService drugService;

    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }
    @GetMapping()
    public ResponseEntity<List<DrugDto>> findAllDrugs() {
        return new ResponseEntity<>(drugService.findAllDrugs(), HttpStatus.OK);
    }
    @GetMapping("/findDrugById/{drugId}")
    public ResponseEntity<DrugDto> findDrugById(@PathVariable Long drugId) {
        try{
            return new ResponseEntity<>(drugService.findById(drugId), HttpStatus.OK);
        } catch (HospitalException hospitalException) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findCompanyByMostUsedDrug")
    public ResponseEntity<?> findCompanyByMostUsedDrug(@RequestParam String genericDrugName) {
        if(drugService.findMostUsedDrugFromCompanyByGenericName(genericDrugName) == null) {
            return ResponseEntity.ok().body("Drug with name " + genericDrugName + "has not been yet used in receipts.");
        }
        return new ResponseEntity<>(drugService.findMostUsedDrugFromCompanyByGenericName(genericDrugName), HttpStatus.OK);
    }

    @GetMapping("/findDrugHistoriesForPatient/{patientId}")
    public ResponseEntity<List<DrugHistoryDto>> findDrugHistoriesForPatient(@PathVariable(name = "patientId") Long patientId) {
        try {
            return new ResponseEntity<>(drugService.findDrugHistoriesForPatient(patientId), HttpStatus.OK);
        } catch (HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
