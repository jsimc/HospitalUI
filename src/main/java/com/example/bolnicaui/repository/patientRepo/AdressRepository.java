package com.example.bolnicaui.repository.patientRepo;

import com.example.bolnicaui.domain.Adress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends JpaRepository<Adress, Long> {
}
