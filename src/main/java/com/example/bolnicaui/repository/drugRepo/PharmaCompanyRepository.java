package com.example.bolnicaui.repository.drugRepo;

import com.example.bolnicaui.domain.drug.PharmaCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmaCompanyRepository extends JpaRepository<PharmaCompany, Long> {
}
