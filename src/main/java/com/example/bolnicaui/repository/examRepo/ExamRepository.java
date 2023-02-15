package com.example.bolnicaui.repository.examRepo;

import com.example.bolnicaui.domain.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
}
