package com.example.bolnicaui.domain;

import com.example.bolnicaui.domain.certificate.PerformedProcedure;
import com.example.bolnicaui.domain.rooms.Discharge;
import com.example.bolnicaui.domain.rooms.HospitalStay;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "price")
    private double price;
    @Column(name = "high_bill")
    private boolean highBill;
    @OneToMany(mappedBy = "bill", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<PerformedProcedure> performedProcedures;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "discharge_id")
    private Discharge discharge;
    @OneToOne(mappedBy = "bill")
    private Discharge discharge1;
    public void addPerformedProcedure(PerformedProcedure performedProcedure) {
        if(performedProcedure == null)
            return;
        if(performedProcedures == null)
            performedProcedures = new ArrayList<>();
        performedProcedures.add(performedProcedure);
        performedProcedure.setBill(this);
    }
}
