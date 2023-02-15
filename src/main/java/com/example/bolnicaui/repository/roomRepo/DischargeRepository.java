package com.example.bolnicaui.repository.roomRepo;

import com.example.bolnicaui.domain.rooms.Discharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DischargeRepository extends JpaRepository<Discharge, Long> {
}
