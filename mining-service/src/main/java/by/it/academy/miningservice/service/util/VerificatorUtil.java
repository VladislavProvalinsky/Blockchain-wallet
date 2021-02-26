package by.it.academy.miningservice.service.util;

import by.it.academy.miningservice.RSA.RSAGenUtil;
import by.it.academy.miningservice.entity.Transaction;
import by.it.academy.miningservice.entity.TransactionStatus;
import by.it.academy.miningservice.handleException.TransactionHashException;

import java.security.PublicKey;
import java.util.List;
import java.util.stream.Collectors;

public class VerificatorUtil {

    public static List<Transaction> verifyTransactionsHash(List<Transaction> transactions) { // 1 step
        return transactions.stream()
                .map(tx -> {
                    try {
                        RSAGenUtil.checkTransactionHash(tx);
                        tx.setStatus(TransactionStatus.HASH_VERIFIED);
                        return tx;
                    } catch (TransactionHashException ex) {
                        tx.setStatus(TransactionStatus.WRONG_HASH);
                        return tx;
                    }
                }).collect(Collectors.toList());
    }

    public static List<Transaction> verifySignatureOfTransactions(List<Transaction> transactions) { // 2 step
        return transactions.stream()
                .map(tx -> {
                    if (tx.getStatus().equals(TransactionStatus.WRONG_HASH)) return tx;
                    else {
                        try {
                            PublicKey publicKey = RSAGenUtil.getPublicFromString(tx.getSenderPublicKey());
                            RSAGenUtil.verifySignature(publicKey, tx.getId(), tx.getSignature());
                            tx.setStatus(TransactionStatus.VERIFIED);
                            return tx;
                        } catch (Exception ex) {
                            tx.setStatus(TransactionStatus.WRONG_SIGNATURE);
                            return tx;
                        }
                    }
                }).collect(Collectors.toList());
    }
}
