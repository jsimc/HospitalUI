package com.example.bolnicaui.service;

import com.example.bolnicaui.domain.exam.ExamType;
import com.example.bolnicaui.dto.ExamDto;
import com.example.bolnicaui.exception.HospitalException;

public interface ExamService {
    ExamDto addExamForPatient(Long patientId, Long examTypeId) throws HospitalException;
}
