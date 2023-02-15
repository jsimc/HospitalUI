package com.example.bolnicaui.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "specialization_type")
public class SpecializationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "specialization_name")
    private String specializationName;

    @OneToMany(mappedBy = "specializationType", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Doctor> doctors;

    public SpecializationType() {
    }

    public SpecializationType(String specializationName) {
        this.specializationName = specializationName;
    }
}
