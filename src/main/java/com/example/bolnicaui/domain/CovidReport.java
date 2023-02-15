package com.example.bolnicaui.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name = "covid_report")
public class CovidReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "currently_sick")
    private boolean currentlySick; //currently has covid
    @Column(name = "date_from")
    private LocalDate dateFrom;
    @Column(name = "date_to")
    private LocalDate dateTo;
    @Column(name = "is_patient")
    private boolean isPatient;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public CovidReport(boolean currentlySick, LocalDate dateFrom, LocalDate dateTo, boolean isPatient, Patient patient, Doctor doctor) {
        this.currentlySick = currentlySick;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.isPatient = isPatient;
        this.patient = patient;
        this.doctor = doctor;
    }

    public CovidReport() {
    }

    public boolean isPatient() {
        return isPatient;
    }

    public void setPatient(boolean patient) {
        isPatient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
