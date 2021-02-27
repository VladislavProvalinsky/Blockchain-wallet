package by.it.academy.miningservice.service;

import by.it.academy.miningservice.entity.Output;
import by.it.academy.miningservice.entity.Transaction;
import by.it.academy.miningservice.entity.TransactionStatus;
import by.it.academy.miningservice.entity.Wallet;
import by.it.academy.miningservice.repository.OutputRepository;
import by.it.academy.miningservice.repository.TransactionRepository;
import by.it.academy.miningservice.repository.WalletRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

@Log
@Service
public class TransactionService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    OutputRepository outputRepository;


    @Transactional
    public List<Transaction> getFirst5NotConfirmedTransactions(TransactionStatus status) {
        List<Transaction> transactions = transactionRepository.findFirst5ByStatus(status);
        transactions.forEach(e -> e.setStatus(TransactionStatus.IN_PROCESS));
        transactionRepository.saveAll(transactions);
        return transactions;
    }

    @Transactional
    public List<Transaction> getNeededNumberOfNotConfirmedTransactions(TransactionStatus status, int number) {
        Query query = entityManager.createQuery("From Transaction where status=:status", Transaction.class);
        query.setParameter("status", status);
        query.setMaxResults(number);
        List<Transaction> resultList = query.getResultList();
        resultList.forEach(tx -> tx.setStatus(TransactionStatus.IN_BLOCK));
        transactionRepository.saveAll(resultList);
        return resultList;
    }

    @Transactional
    public List<Transaction> findFirst5ByStatusAndComissionGreaterThan(TransactionStatus status, BigDecimal comission) {
        List<Transaction> transactions = transactionRepository.findFirst5ByStatusAndComissionGreaterThan(status, comission);
        transactions.forEach(e -> e.setStatus(TransactionStatus.IN_BLOCK));
        transactionRepository.saveAll(transactions);
        return transactions;
    }

    @Transactional
    public void rollbackTransactionsFromBlock(ArrayBlockingQueue<Transaction> queue) {
        if (!queue.isEmpty()) {
            List<Transaction> transactions = new ArrayList<>(queue);
            transactions.forEach(tx -> tx.setStatus(TransactionStatus.VERIFIED));
            transactionRepository.saveAll(transactions);
        }
    }

    public void putCheckedTransactionsInDB(List<Transaction> transactions) {
        List<Transaction> verifiedTX = transactions.stream()
                .filter(tx -> tx.getStatus().equals(TransactionStatus.VERIFIED))
                .collect(Collectors.toList());

        List<Transaction> wrongTransactions = transactions.stream()
                .filter(tx -> !tx.getStatus().equals(TransactionStatus.VERIFIED))
                .collect(Collectors.toList());

        putVerifiedTransactions(verifiedTX);
        putWrongTransactions(wrongTransactions);
    }

    @Transactional
    private void putWrongTransactions(List<Transaction> wrongTransactions) {
        transactionRepository.saveAll(wrongTransactions);
        wrongTransactions.forEach(tx -> {
            Wallet wallet = walletRepository.findById(tx.getSenderPublicKey()).get();
            Output output = wallet.getOutputs().stream()
                    .filter(o -> o.getValue().equals(tx.getValue().add(tx.getComission())))
                    .findFirst()
                    .get();
            outputRepository.deleteById(output.getId());
        });
    }

    @Transactional
    private void putVerifiedTransactions(List<Transaction> verifiedTX) {
        transactionRepository.saveAll(verifiedTX);
    }

}
