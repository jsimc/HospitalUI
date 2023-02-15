package com.example.bolnicaui.repository.doctorRepo;

import com.example.bolnicaui.domain.SpecializationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationTypeRepository extends JpaRepository<SpecializationType, Long> {
}