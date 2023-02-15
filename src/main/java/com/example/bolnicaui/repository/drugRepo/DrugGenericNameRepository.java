package com.example.bolnicaui.repository.drugRepo;

import com.example.bolnicaui.domain.drug.GenericDrugName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugGenericNameRepository extends JpaRepository<GenericDrugName, Long> {
    GenericDrugName findGenericDrugNameByName(String name);
}
