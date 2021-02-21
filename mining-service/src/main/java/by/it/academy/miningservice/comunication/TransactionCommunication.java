package by.it.academy.miningservice.comunication;

import by.it.academy.miningservice.RSA.RSAGenUtil;
import by.it.academy.miningservice.dto.Transaction;
import by.it.academy.miningservice.dto.TransactionStatus;
import by.it.academy.miningservice.handleException.TransactionHashException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Log
public class TransactionCommunication {

    public static final String TxURL = "http://localhost:8080/blockchain/transactions";

    @Autowired
    private RestTemplate restTemplate;

    public List<Transaction> getFirst5NotConfirmedTransactions (TransactionStatus status) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TxURL)
                .queryParam("status", status);
        ResponseEntity<List<Transaction>> exchange = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Transaction>>() {
                });
        return exchange.getBody();
    }

    public List<Transaction> findFirst5ByStatusAndComissionGreaterThan (TransactionStatus status, BigDecimal comission) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TxURL+"/getVerified")
                .queryParam("status", status)
                .queryParam("comission", comission);
        ResponseEntity<List<Transaction>> exchange = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Transaction>>() {
                });
        return exchange.getBody();
    }

    public void putCheckedTransactionsInDB (List<Transaction> transactions) { // 3 step
        List<Transaction> transactionsHash = verifyTransactionsHash(transactions);
        List<Transaction> resultTransactions = verifySignatureOfTransactions(transactionsHash);
        log.info("Resulting transactions.....");
        resultTransactions.forEach(System.out::println);
        restTemplate.put(TxURL+"/putVerified", resultTransactions);
    }


    public List<Transaction> verifyTransactionsHash(List<Transaction> transactions) { // 1 step
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

    public List<Transaction> verifySignatureOfTransactions (List<Transaction> transactions){ // 2 step
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
