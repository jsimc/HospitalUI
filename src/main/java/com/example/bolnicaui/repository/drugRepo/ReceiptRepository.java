package com.example.bolnicaui.repository.drugRepo;

import com.example.bolnicaui.domain.drug.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
