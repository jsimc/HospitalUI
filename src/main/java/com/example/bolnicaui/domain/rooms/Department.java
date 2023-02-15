package com.example.bolnicaui.domain.rooms;

import com.example.bolnicaui.domain.ChosenDoctor;
import com.example.bolnicaui.domain.CovidReport;
import com.example.bolnicaui.domain.duty.DutyDoctor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "department")
    private List<Block> blocks;
    @OneToMany(mappedBy = "department")
    private List<DoctorDepartment> doctorDepartments;
    @OneToMany(mappedBy = "department")
    private List<DutyDoctor> dutyDoctors;
    @OneToMany(mappedBy = "department")
    private List<NurseDepartment> nurseDepartments;
    public void addDoctorDepartment(DoctorDepartment doctorDepartment){
        if(doctorDepartment == null)
            return;
        if(doctorDepartments == null)
            doctorDepartments = new ArrayList<>();
        doctorDepartments.add(doctorDepartment);
        doctorDepartment.setDepartment(this);
    }
}
