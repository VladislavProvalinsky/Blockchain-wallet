package by.it.academy.blockchain.service;

import by.it.academy.blockchain.RSA.RSAGenUtil;
import by.it.academy.blockchain.entity.Output;
import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.enums.TransactionStatus;
import by.it.academy.blockchain.repository.InputRepository;
import by.it.academy.blockchain.repository.OutputRepository;
import by.it.academy.blockchain.repository.TransactionRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Log
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    OutputRepository outputRepository;

    @Autowired
    InputRepository inputRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    WalletService walletService;


    public void putNewTransaction(Wallet wallet, Transaction transaction, String privateKey) {
        // сетим время и статус
        transaction.setDate(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.NOT_CONFIRMED);
        // считаем хеш и сетим в id
        String hashTransaction = RSAGenUtil.hashTransaction(transaction);
        transaction.setId(hashTransaction);
        // подписываем трназакцию и сетим цифр подпись
        String signature = RSAGenUtil.getSignature(RSAGenUtil.getPrivateFromString(privateKey), hashTransaction);
        transaction.setSignature(signature);
        // сетим в кошелек готовую транзакцию
        wallet.getTransactions().add(transaction);
        // обновляем аутпут кошелька
        wallet.getOutputs().add(new Output(transaction.getValue().add(transaction.getComission())));
        // апдейтим кошелек
        walletService.updateWallet(wallet);
    }

    public List<Transaction> getFirst5NotConfirmedTransactions(TransactionStatus status) {
        return transactionRepository.findFirst5ByStatus(status);
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

    private void putWrongTransactions(List<Transaction> wrongTransactions) {
        transactionRepository.saveAll(wrongTransactions);
        wrongTransactions.forEach(tx -> {
            Wallet wallet = walletService.getOne(tx.getSenderPublicKey());
            Output output = wallet.getOutputs().stream()
                    .filter(o -> o.getValue().equals(tx.getValue().add(tx.getComission())))
                    .findFirst()
                    .get();
            outputRepository.deleteById(output.getId());
        });
    }

    private void putVerifiedTransactions(List<Transaction> verifiedTX) {
        transactionRepository.saveAll(verifiedTX);
    }

    public List<Transaction> findFirst5ByStatusAndComissionGreaterThan(TransactionStatus status, BigDecimal comission) {
        return transactionRepository.findFirst5ByStatusAndComissionGreaterThan(status, comission);
    }
}
