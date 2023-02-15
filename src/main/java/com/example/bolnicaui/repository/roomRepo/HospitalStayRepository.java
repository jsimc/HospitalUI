package com.example.bolnicaui.repository.roomRepo;

import com.example.bolnicaui.domain.rooms.HospitalStay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalStayRepository extends JpaRepository<HospitalStay, Long> {
}
