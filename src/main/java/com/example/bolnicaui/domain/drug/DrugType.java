package com.example.bolnicaui.domain.drug;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "drug_type")
public class DrugType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "type")
    private String type;
    @OneToMany(mappedBy = "drugType", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Drug> drugs;

    public DrugType(String type) {
        this.type = type;
    }

    public DrugType() {
    }

    @Override
    public String toString() {
        return "DrugType{" +
                "type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrugType drugType = (DrugType) o;
        return Objects.equals(id, drugType.id) && Objects.equals(type, drugType.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
