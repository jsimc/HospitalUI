package com.example.bolnicaui.domain.drug;

import com.example.bolnicaui.domain.Patient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "drug_history")
public class DrugHistory{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "date_from")
    private LocalDate dateFrom;
    @Column(name = "date_to")
    private LocalDate dateTo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id")
    private Drug drug;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public DrugHistory() {
    }

    public DrugHistory(LocalDate from, LocalDate to, Drug drug, Patient patient) {
        this.dateFrom = from;
        this.dateTo = to;
        this.drug = drug;
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "DrugHistory{" +
                " drug=" + drug +
                ", patient=" + patient +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrugHistory that = (DrugHistory) o;
        return Objects.equals(id, that.id)
                && Objects.equals(dateFrom, that.dateFrom)
                && Objects.equals(dateTo, that.dateTo)
                && Objects.equals(drug, that.drug)
                && Objects.equals(patient, that.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateFrom, dateTo, drug, patient);
    }
}
