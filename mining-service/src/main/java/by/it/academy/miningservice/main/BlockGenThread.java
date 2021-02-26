package by.it.academy.miningservice.main;

import by.it.academy.miningservice.entity.Block;
import by.it.academy.miningservice.entity.Transaction;
import by.it.academy.miningservice.entity.TransactionStatus;
import by.it.academy.miningservice.service.BlockService;
import by.it.academy.miningservice.service.TransactionService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

@Log
@Data
public class BlockGenThread implements Runnable{

    private String walletId;
    private volatile boolean stopFlag = false;
    private BlockService blockService;
    private TransactionService transactionService;

    public BlockGenThread(@Autowired BlockService blockService, @Autowired TransactionService transactionService, String walletId) {
        this.blockService = blockService;
        this.transactionService = transactionService;
        this.walletId = walletId;
    }

    @Override
    public void run() {
        ArrayBlockingQueue<Transaction> queue = new ArrayBlockingQueue<>(5, true);
        while (stopFlag) {
            queue.addAll(transactionService.findFirst5ByStatusAndComissionGreaterThan(TransactionStatus.VERIFIED, BigDecimal.ZERO));
            if (queue.size() == 5) {
                Block block = blockService.packageTransactionsInBlock(queue);
                blockService.saveBlockInDB(block);
            } else {
                List<Transaction> transactions =
                        transactionService.getNeededNumberOfNotConfirmedTransactions(TransactionStatus.VERIFIED, 5-queue.size());
                transactions.forEach(queue::offer);
                Block block = blockService.packageTransactionsInBlock(queue);
                blockService.saveBlockInDB(block);
            }
            queue.clear();
        }

    }
}
