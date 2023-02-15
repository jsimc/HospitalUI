package com.example.bolnicaui.domain;

import com.example.bolnicaui.domain.certificate.DoctorCertificate;
import com.example.bolnicaui.domain.certificate.PerformedProcedure;
import com.example.bolnicaui.domain.drug.Receipt;
import com.example.bolnicaui.domain.duty.DutyDoctor;
import com.example.bolnicaui.domain.exam.Exam;
import com.example.bolnicaui.domain.overtime.DoctorOvertime;
import com.example.bolnicaui.domain.rooms.Discharge;
import com.example.bolnicaui.domain.rooms.DoctorDepartment;
import com.example.bolnicaui.domain.vacation.DoctorHoliday;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "free_time_days")
    private Long freeTimeDays;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_type_id")
    private SpecializationType specializationType;
    @OneToMany(mappedBy = "doctor")
    private List<Exam> exams;
    @OneToMany(mappedBy = "doctor")
    private List<Receipt> receipts;
    @OneToMany(mappedBy = "doctor")
    private List<ChosenDoctor> chosenDoctors;
    @OneToMany(mappedBy = "doctor")
    private List<DoctorDepartment> doctorDepartments;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<DoctorCertificate> doctorCertificates;
    @OneToMany(mappedBy = "doctor", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<PerformedProcedure> performedProcedures;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<CovidReport> covidReports;
    @OneToMany(mappedBy = "doctor", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<DutyDoctor> dutyDoctors;
    @OneToMany(mappedBy = "doctor", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<DoctorOvertime> doctorOvertimes;
    @OneToMany(mappedBy = "doctor", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<DoctorHoliday> doctorHolidays;
    @OneToMany(mappedBy = "doctor")
    private List<Discharge> discharges;

    public void addChosenDoctor(ChosenDoctor chosenDoctor) {
        if(chosenDoctor == null)
            return;
        if(chosenDoctors == null)
            chosenDoctors = new ArrayList<>();
        chosenDoctors.add(chosenDoctor);
        chosenDoctor.setDoctor(this);
    }
    public void addReceipt(Receipt receipt) {
        if(receipt == null)
            return;
        if(receipts == null)
            receipts = new ArrayList<>();
        receipts.add(receipt);
        receipt.setDoctor(this);
    }
    public void addPerformedProcedure(PerformedProcedure performedProcedure) {
        if(performedProcedure == null)
            return;
        if (performedProcedures == null) {
            performedProcedures = new ArrayList<>();
        }
        this.performedProcedures.add(performedProcedure);
        performedProcedure.setDoctor(this);
    }
    public void addDoctorDepartment(DoctorDepartment doctorDepartment) {
        if(doctorDepartment == null)
            return;
        if (doctorDepartments == null) {
            doctorDepartments = new ArrayList<>();
        }
        this.doctorDepartments.add(doctorDepartment);
        doctorDepartment.setDoctor(this);
    }
    public void addDoctorCertificate(DoctorCertificate doctorCertificate) {
        if(doctorCertificate == null)
            return;
        if (doctorCertificates == null) {
            doctorCertificates = new ArrayList<>();
        }
        this.doctorCertificates.add(doctorCertificate);
        doctorCertificate.setDoctor(this);
    }
    public void addDutyDoctor(DutyDoctor dutyDoctor) {
        if(dutyDoctor == null)
            return;
        if (dutyDoctors == null) {
            dutyDoctors = new ArrayList<>();
        }
        this.dutyDoctors.add(dutyDoctor);
        dutyDoctor.setDoctor(this);
    }
    public void addDoctorOvertime(DoctorOvertime doctorOvertime) {
        if(doctorOvertime == null)
            return;
        if (doctorOvertimes == null) {
            doctorOvertimes = new ArrayList<>();
        }
        this.doctorOvertimes.add(doctorOvertime);
        doctorOvertime.setDoctor(this);
    }
    public void addDischarge(Discharge discharge) {
        if(discharge == null)
            return;
        if (discharges == null) {
            discharges = new ArrayList<>();
        }
        this.discharges.add(discharge);
        discharge.setDoctor(this);
    }
    //TODO doctorHoliday ? oduzima se od Doctor.free_days i proverava se da li je ta vrednost == 0
    public void addDoctorHoliday(DoctorHoliday doctorHoliday) {
        if(doctorHoliday == null)
            return;
        long holidayDays = doctorHoliday.getDateFrom().until(doctorHoliday.getDateTo(), ChronoUnit.DAYS);
        if(this.freeTimeDays < holidayDays)
            return;
        if(doctorHolidays == null)
            doctorHolidays = new ArrayList<>();
        this.freeTimeDays -= holidayDays;
        this.doctorHolidays.add(doctorHoliday);
        doctorHoliday.setDoctor(this);
    }
    public void addCovidReport(CovidReport covidReport) {
        if(covidReport == null)
            return;
        if (covidReports == null) {
            covidReports = new ArrayList<>();
        }
        this.covidReports.add(covidReport);
        covidReport.setDoctor(this);
        covidReport.setPatient(false);
    }
    public void addExam(Exam exam) {
        if(exam == null)
            return;
        if (exams == null) {
            exams = new ArrayList<>();
        }
        this.exams.add(exam);
        exam.setDoctor(this);
    }
    public Doctor() {
    }

    public Doctor(String firstName, String lastName, Long freeTimeDays, SpecializationType specializationType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.freeTimeDays = freeTimeDays;
        this.specializationType = specializationType;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specializationType=" + specializationType +
                '}';
    }
}
