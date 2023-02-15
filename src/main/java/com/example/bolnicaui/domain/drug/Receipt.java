package com.example.bolnicaui.domain.drug;

import com.example.bolnicaui.domain.Doctor;
import com.example.bolnicaui.domain.Patient;
import com.example.bolnicaui.domain.exam.Exam;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "receipt")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "self_renewing")
    private boolean selfRenewing;
    @Column(name = "issue_date")
    private LocalDate issueDate;
    @Column(name = "expire_date")
    private LocalDate expireDate;

    //TODO jel pod dozom leka misli na quantity ili na double, kao mg?
    @Column(name = "dosage")
    private int dosage; //quantity
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "drug_id")
    private Drug drug;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "exam_id", nullable = true)
    private Exam exam;

    @Override
    public String toString() {
        return "Receipt{" +
                " doctor=" + doctor +
                ", patient=" + patient +
                ", drug=" + drug +
                ", dosage=" + dosage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return selfRenewing == receipt.selfRenewing && dosage == receipt.dosage && Objects.equals(id, receipt.id) && Objects.equals(issueDate, receipt.issueDate) && Objects.equals(expireDate, receipt.expireDate) && Objects.equals(doctor, receipt.doctor) && Objects.equals(patient, receipt.patient) && Objects.equals(drug, receipt.drug) && Objects.equals(exam, receipt.exam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, selfRenewing, issueDate, expireDate, dosage, doctor, patient, drug, exam);
    }
}
