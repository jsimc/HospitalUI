package com.example.bolnicaui.controller;

import com.example.bolnicaui.domain.exam.ExamType;
import com.example.bolnicaui.dto.ExamDto;
import com.example.bolnicaui.exception.HospitalException;
import com.example.bolnicaui.service.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
public class ExamController {
    ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/addExam")
    public ResponseEntity<ExamDto> addExamForPatient(@RequestParam(name = "patientId") Long patientId,
                                                     @RequestParam(name = "examTypeId") Long examTypeId) {
        try {

            return new ResponseEntity<>(examService.addExamForPatient(patientId, examTypeId), HttpStatus.CREATED);
        } catch (HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
