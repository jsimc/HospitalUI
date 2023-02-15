package com.example.bolnicaui.domain.rooms;

import com.example.bolnicaui.domain.Bill;
import com.example.bolnicaui.domain.Doctor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "discharge")
public class Discharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "hospital_stay_id")
    private HospitalStay hospitalStay;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_id")
    private Bill bill;
    @OneToOne(mappedBy = "discharge")
    private Bill bill1;
    public void setHospitalStay(HospitalStay hospitalStay) {
        hospitalStay.setCurrentlyActive(false);
        hospitalStay.setDateTo(LocalDate.now());
        hospitalStay.setDischarge(this);
        this.hospitalStay = hospitalStay;
    }

    public void setBill(Bill bill) {
        bill.setDischarge(this);
        this.bill = bill;
    }
}
