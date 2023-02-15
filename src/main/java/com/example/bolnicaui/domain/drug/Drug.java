package com.example.bolnicaui.domain.drug;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "drug")
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "drug_name")
    private String drug_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharma_company_id")
    private PharmaCompany pharmaCompany;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generic_drug_name_id")
    private GenericDrugName genericDrugName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_type_id")
    private DrugType drugType;
    @OneToMany(mappedBy = "drug")
    private List<DrugHistory> drugHistory;
    @OneToMany(mappedBy = "drug")
    private List<Receipt> receipts;

    public void addReceipt(Receipt receipt) {
        if(receipt == null)
            return;
        if(receipts == null)
            receipts = new ArrayList<>();
        receipts.add(receipt);
        receipt.setDrug(this);
    }


    public Drug() {
    }

    public Drug(String drug_name, PharmaCompany pharmaCompany, GenericDrugName genericDrugName, DrugType drugType) {
        this.drug_name = drug_name;
        this.pharmaCompany = pharmaCompany;
        this.genericDrugName = genericDrugName;
        this.drugType = drugType;
    }
}
