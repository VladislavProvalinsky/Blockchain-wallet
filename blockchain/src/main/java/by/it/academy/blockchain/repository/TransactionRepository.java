package by.it.academy.blockchain.repository;

import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findFirst5ByStatus(TransactionStatus status);
    List<Transaction> findFirst5ByStatusAndComissionGreaterThan(TransactionStatus status, BigDecimal comission);
}
