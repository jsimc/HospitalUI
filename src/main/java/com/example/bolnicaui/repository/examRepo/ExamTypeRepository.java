package com.example.bolnicaui.repository.examRepo;

import com.example.bolnicaui.domain.exam.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamTypeRepository extends JpaRepository<ExamType, Long> {
}
