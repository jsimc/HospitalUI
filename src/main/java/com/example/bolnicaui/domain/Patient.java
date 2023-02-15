package com.example.bolnicaui.domain;

import com.example.bolnicaui.domain.certificate.PerformedProcedure;
import com.example.bolnicaui.domain.diagnosis.PatientDiagnosisHistory;
import com.example.bolnicaui.domain.drug.DrugHistory;
import com.example.bolnicaui.domain.drug.Receipt;
import com.example.bolnicaui.domain.exam.Exam;
import com.example.bolnicaui.domain.insurance.PatientInsurance;
import com.example.bolnicaui.domain.rooms.HospitalStay;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "JMBG")
    private String JMBG;
    @Column(name = "phone_number")
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "adress_id")
    private Adress adress;
    @OneToMany(mappedBy = "patient", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<DrugHistory> drugHistories;
    @OneToMany(mappedBy = "patient", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<PatientDiagnosisHistory> patientDiagnosisHistories;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<CovidReport> covidReports;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PatientInsurance> patientInsurances;
    @OneToMany(mappedBy = "patient", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Exam> exams;
    @OneToMany(mappedBy = "patient", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Receipt> receipts;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<ChosenDoctor> chosenDoctors;
    @OneToMany(mappedBy = "patient", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    private List<HospitalStay> hospitalStays;
    @OneToMany(mappedBy = "patient", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<PerformedProcedure> performedProcedures;



    public Patient() {
    }

    public Patient(String firstName, String lastName, String JMBG, String phoneNumber, Adress adress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.JMBG = JMBG;
        this.phoneNumber = phoneNumber;
        this.adress = adress;
    }
    public void addDrugHistory(DrugHistory drugHistory) {
        if(drugHistory == null)
            return;
        if (drugHistories == null) {
            drugHistories = new ArrayList<>();
        }
        this.drugHistories.add(drugHistory);
        drugHistory.setPatient(this);
    }
    public void addPatientDiagnosisHistory(PatientDiagnosisHistory patientDiagnosisHistory) {
        if(patientDiagnosisHistory == null)
            return;
        if (patientDiagnosisHistories == null) {
            patientDiagnosisHistories = new ArrayList<>();
        }
        this.patientDiagnosisHistories.add(patientDiagnosisHistory);
        patientDiagnosisHistory.setPatient(this);
    }
    public void addCovidReport(CovidReport covidReport) {
        if(covidReport == null)
            return;
        if (covidReports == null) {
            covidReports = new ArrayList<>();
        }
        this.covidReports.add(covidReport);
        covidReport.setPatient(this);
        covidReport.setPatient(true);
        covidReport.setDateFrom(LocalDate.now());
        covidReport.setCurrentlySick(true);
    }
    public void addReceipt(Receipt receipt) {
        if(receipt == null)
            return;
        if (receipts == null) {
            receipts = new ArrayList<>();
        }
        this.receipts.add(receipt);
        receipt.setPatient(this);
    }

    /**
     * Adds Current Chosen Doctor
     * @param chosenDoctor
     */
    public void addChosenDoctor(ChosenDoctor chosenDoctor) {
        if(chosenDoctor == null)
            return;
        if (chosenDoctors == null) {
            chosenDoctors = new ArrayList<>();
        }
        this.chosenDoctors.stream()
                .filter(ChosenDoctor::isCurrently)
                .forEach(chosenDoctor1 -> {
                    chosenDoctor1.setToDate(LocalDate.now());
                    chosenDoctor1.setCurrently(false);
                });
        this.chosenDoctors.add(chosenDoctor);
        chosenDoctor.setPatient(this);
        chosenDoctor.setFromDate(LocalDate.now());
        chosenDoctor.setCurrently(true);
    }
    public void addHospitalStay(HospitalStay hospitalStay) {
        if(hospitalStay == null)
            return;
        if (hospitalStays == null) {
            hospitalStays = new ArrayList<>();
        }
        this.hospitalStays.add(hospitalStay);
        hospitalStay.setDateFrom(LocalDate.now());
        hospitalStay.setCurrentlyActive(true);
        hospitalStay.setPatient(this);
    }
    public void addPerformedProcedure(PerformedProcedure performedProcedure) {
        if(performedProcedure == null)
            return;
        if (performedProcedures == null) {
            performedProcedures = new ArrayList<>();
        }
        this.performedProcedures.add(performedProcedure);
        performedProcedure.setPatient(this);
    }
    public void addExam(Exam exam) {
        if(exam == null)
            return;
        if (exams == null) {
            exams = new ArrayList<>();
        }
        this.exams.add(exam);
        exam.setPatient(this);
    }
    public void addPatientInsurance(PatientInsurance patientInsurance) {
        if(patientInsurance == null)
            return;
        if (patientInsurances == null) {
            patientInsurances = new ArrayList<>();
        }
        this.patientInsurances.add(patientInsurance);
        patientInsurance.setPatient(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Patient patient = (Patient) o;
        return JMBG.equals(patient.JMBG) && Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", JMBG='" + JMBG + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", adress=" + adress +
                '}';
    }

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getJMBG() {
//        return JMBG;
//    }
//
//    public void setJMBG(String JMBG) {
//        this.JMBG = JMBG;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public Adress getAdress() {
//        return adress;
//    }
//
//    public void setAdress(Adress adress) {
//        this.adress = adress;
//    }
//
//    public List<DrugHistory> getDrugHistories() {
//        return drugHistories;
//    }
//
//    public void setDrugHistories(List<DrugHistory> drugHistories) {
//        this.drugHistories = drugHistories;
//    }
//
//    public List<PatientDiagnosisHistory> getPatientDiagnosisHistories() {
//        return patientDiagnosisHistories;
//    }
//
//    public void setPatientDiagnosisHistories(List<PatientDiagnosisHistory> patientDiagnosisHistories) {
//        this.patientDiagnosisHistories = patientDiagnosisHistories;
//    }
//
//    public List<CovidReport> getCovidReports() {
//        return covidReports;
//    }
//
//    public void setCovidReports(List<CovidReport> covidReports) {
//        this.covidReports = covidReports;
//    }
//
//    public List<PatientInsurance> getPatientInsurances() {
//        return patientInsurances;
//    }
//
//    public void setPatientInsurances(List<PatientInsurance> patientInsurances) {
//        this.patientInsurances = patientInsurances;
//    }
//
//    public List<Exam> getExams() {
//        return exams;
//    }
//
//    public void setExams(List<Exam> exams) {
//        this.exams = exams;
//    }
//
//    public List<Receipt> getReceipts() {
//        return receipts;
//    }
//
//    public void setReceipts(List<Receipt> receipts) {
//        this.receipts = receipts;
//    }
//
//    public List<ChosenDoctor> getChosenDoctors() {
//        return chosenDoctors;
//    }
//
//    public void setChosenDoctors(List<ChosenDoctor> chosenDoctors) {
//        this.chosenDoctors = chosenDoctors;
//    }
//
//    public List<HospitalStay> getHospitalStays() {
//        return hospitalStays;
//    }
//
//    public void setHospitalStays(List<HospitalStay> hospitalStays) {
//        this.hospitalStays = hospitalStays;
//    }
//
//    public List<PerformedProcedure> getPerformedProcedures() {
//        return performedProcedures;
//    }
//
//    public void setPerformedProcedures(List<PerformedProcedure> performedProcedures) {
//        this.performedProcedures = performedProcedures;
//    }
}
