package com.example.bolnicaui.repository.drugRepo;

import com.example.bolnicaui.domain.Patient;
import com.example.bolnicaui.domain.drug.Drug;
import com.example.bolnicaui.domain.drug.PharmaCompany;
import com.example.bolnicaui.dto.DrugDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
    @Modifying
    @Query(nativeQuery = true,
            value = "select concat(p.first_name, ' ', p.last_name) as patientName, d.drug_name as drugName " +
            "from patient_diagnosis_history " +
            "join (select * from drug_history " +
            "group by patient_id, drug_id) as pom using(patient_id) " +
            "join patient p on (p.id = pom.patient_id) " +
            "join drug d on(d.id = pom.drug_id) " +
            "where diagnosis_id = ? " +
            "group by patient_id;"
    )
    List<PatientDrugI> findMostCommonDrugPerPatientForGivenDiagnosis(Long diagnosisId);
    @Modifying
    @Query(nativeQuery = true,
            value = "select pc.name as pharmaCompanyName, gdn.name as genericDrugName from generic_drug_name gdn " +
            "join drug d on (gdn.id = d.generic_drug_name_id) " +
            "join receipt r on (r.drug_id = d.id) " +
            "join pharma_company pc on(d.pharma_company_id = pc.id) " +
            "where gdn.id like ? " +
            "group by pharma_company_id " +
            "order by count(pharma_company_id) desc;")
    List<PharmaCompanyDrugI> findMostUsedDrugFromCompany(Long genericDrugNameId);
}
