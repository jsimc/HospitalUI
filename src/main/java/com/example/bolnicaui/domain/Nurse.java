package com.example.bolnicaui.domain;

import com.example.bolnicaui.domain.certificate.PerformedProcedure;
import com.example.bolnicaui.domain.duty.DutyNurse;
import com.example.bolnicaui.domain.exam.Exam;
import com.example.bolnicaui.domain.overtime.NurseOvertime;
import com.example.bolnicaui.domain.rooms.NurseDepartment;
import com.example.bolnicaui.domain.vacation.NurseHoliday;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nurse")
public class Nurse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "working_licence")
    private boolean workingLicence;
    @Column(name = "free_time_days")
    private int freeTimeDays;
    @OneToMany(mappedBy = "nurse")
    private List<NurseDepartment> nurseDepartments;
    @OneToMany(mappedBy = "nurse", cascade = {CascadeType.ALL})
    private List<Exam> exams;
    @OneToMany(mappedBy = "nurse")
    private List<PerformedProcedure> performedProcedures;
    @OneToMany(mappedBy = "nurse")
    private List<NurseOvertime> nurseOvertimes;
    @OneToMany(mappedBy = "nurse")
    private List<DutyNurse> dutyNurses;
    @OneToMany(mappedBy = "nurse")
    private List<NurseHoliday> nurseHolidays;
    public void addExam(Exam exam) {
        if(exam == null)
            return;
        if(exams == null)
            exams = new ArrayList<>();
        exams.add(exam);
        exam.setNurse(this);
    }

    @Override
    public String toString() {
        return "Nurse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", workingLicence=" + workingLicence +
                '}';
    }
}
