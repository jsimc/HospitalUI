package com.example.bolnicaui.domain.certificate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "medical_procedure")
public class MedicalProcedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "code", unique = true)
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "covid_procedure")
    private boolean covidProcedure;
    @OneToMany(mappedBy = "medicalProcedure")
    private List<Certificate> certificates;
    @OneToMany(mappedBy = "medicalProcedure")
    private List<PerformedProcedure> performedProcedures;

}
