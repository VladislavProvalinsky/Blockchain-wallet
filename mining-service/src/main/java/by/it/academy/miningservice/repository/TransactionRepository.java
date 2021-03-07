package by.it.academy.miningservice.repository;


import by.it.academy.miningservice.entity.Transaction;
import by.it.academy.miningservice.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findFirst5ByStatus(TransactionStatus status);
    List<Transaction> findFirst5ByStatusAndComissionGreaterThanOrderByComissionDesc(TransactionStatus status, BigDecimal comission);
}
