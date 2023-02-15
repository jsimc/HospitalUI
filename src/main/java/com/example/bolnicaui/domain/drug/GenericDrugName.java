package com.example.bolnicaui.domain.drug;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "generic_drug_name")
public class GenericDrugName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "genericDrugName", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Drug> drugs;

    public GenericDrugName() {
    }

    public GenericDrugName(String name) {
        this.name = name;
    }
}
