package by.it.academy.miningservice.main;

import by.it.academy.miningservice.entity.Transaction;
import by.it.academy.miningservice.entity.TransactionStatus;
import by.it.academy.miningservice.service.TransactionService;
import by.it.academy.miningservice.service.util.VerificatorUtil;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Log
@Data
public class TXVerificationThread implements Runnable{

    private String walletId;
    private volatile boolean stopFlag = false;
    private TransactionService transactionService;


    public TXVerificationThread(@Autowired TransactionService transactionService, String walletId) {
        this.transactionService = transactionService;
        this.walletId = walletId;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (stopFlag) {
            log.info("Starting verifying...");
            List<Transaction> first5NotConfirmedTransactions =
                    transactionService.getFirst5NotConfirmedTransactions(TransactionStatus.NOT_CONFIRMED);
            if (first5NotConfirmedTransactions.isEmpty()) {
                log.info("No transactions for verifying. Waiting...");
            } else {
                first5NotConfirmedTransactions.forEach(System.out::println);
                List<Transaction> transactionsHash = VerificatorUtil.verifyTransactionsHash(first5NotConfirmedTransactions);
                List<Transaction> resultTransactions = VerificatorUtil.verifySignatureOfTransactions(transactionsHash);
                transactionService.putCheckedTransactionsInDB(resultTransactions);
                log.info("Waiting 1 minute....");
            }
            timer();
        }
    }

    public static void timer() throws InterruptedException {
        Thread.sleep(60_000);
        System.out.println("Начинаем верификацию транзакций!");
    }
}
