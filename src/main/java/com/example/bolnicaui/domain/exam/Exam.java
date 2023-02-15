package com.example.bolnicaui.domain.exam;

import com.example.bolnicaui.domain.Doctor;
import com.example.bolnicaui.domain.Nurse;
import com.example.bolnicaui.domain.Patient;
import com.example.bolnicaui.domain.drug.Receipt;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "exam_date")
    private LocalDate examDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id")
    private Nurse nurse;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_type_id")
    private ExamType examType;
    @OneToMany(mappedBy = "exam")
    private List<Receipt> receipts;

    public void addReceipt(Receipt receipt) {
        if(receipt == null)
            return;
        if(receipts == null)
            receipts = new ArrayList<>();
        receipts.add(receipt);
        receipt.setExam(this);
    }

    @Override
    public String toString() {
        return "Exam{" +
                "doctor=" + doctor +
                ", patient=" + patient +
                ", nurse=" + nurse +
                ", examType=" + examType +
                '}';
    }
}
