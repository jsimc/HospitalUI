package com.example.bolnicaui.mapper;

import com.example.bolnicaui.domain.drug.Receipt;
import com.example.bolnicaui.domain.exam.Exam;
import com.example.bolnicaui.dto.ExamDto;
import org.springframework.stereotype.Component;
import static com.example.bolnicaui.exception.DoNothingException.*;

import java.util.stream.Collectors;

@Component
public class ExamMapper {
    public ExamDto mapToExamDto(Exam exam) {
        ExamDto examDto = new ExamDto();
        examDto.setId(exam.getId());
        examDto.setExamDate(exam.getExamDate());
        examDto.setDoctor(exam.getDoctor().getId());
        examDto.setNurse(exam.getNurse().getId());
        examDto.setPatient(exam.getPatient().getId());
        examDto.setExamType(exam.getExamType().getId());
        ignoringExc(() -> examDto.setReceipts(exam.getReceipts().stream().map(Receipt::getId).toList()));
        return examDto;
    }
}
