package by.it.academy.blockchain.service;

import by.it.academy.blockchain.RSA.RSAGenUtil;
import by.it.academy.blockchain.entity.Output;
import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.enums.TransactionStatus;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Log
public class TransactionService {

    @Autowired
    WalletService walletService;

    @Transactional
    public void putNewTransaction(Wallet wallet, Transaction transaction, String privateKey) {
        // сетим время и статус
        transaction.setDate(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.NOT_CONFIRMED);
        // считаем хеш и сетим в id
        String hashTransaction = RSAGenUtil.hashTransaction(transaction);
        transaction.setId(hashTransaction);
        // подписываем трназакцию и сетим цифр подпись
        String signature = RSAGenUtil.getSignature(RSAGenUtil.getPrivateFromString(privateKey), hashTransaction);
        transaction.setSignature(signature == null ? "Invalid signature" : signature);
        // сетим в кошелек готовую транзакцию
        wallet.getTransactions().add(transaction);
        // обновляем аутпут кошелька
        wallet.getOutputs().add(new Output(transaction.getValue().add(transaction.getComission())));
        // апдейтим кошелек
        walletService.updateWallet(wallet);
    }
}
