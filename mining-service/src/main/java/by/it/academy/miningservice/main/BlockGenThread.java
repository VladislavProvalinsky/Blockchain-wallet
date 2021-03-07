package by.it.academy.miningservice.main;

import by.it.academy.miningservice.entity.Block;
import by.it.academy.miningservice.entity.Transaction;
import by.it.academy.miningservice.entity.TransactionStatus;
import by.it.academy.miningservice.service.BlockService;
import by.it.academy.miningservice.service.TransactionService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

@Log
@Data
@EqualsAndHashCode(callSuper = true)
public class BlockGenThread extends Thread {

    private String walletId;
    private BlockService blockService;
    private TransactionService transactionService;

    public BlockGenThread(@Autowired BlockService blockService, @Autowired TransactionService transactionService, String walletId) {
        this.blockService = blockService;
        this.transactionService = transactionService;
        this.walletId = walletId;
    }

    @SneakyThrows
    @Override
    public void run() {
        ArrayBlockingQueue<Transaction> queue = new ArrayBlockingQueue<>(5, true);
        while (!this.isInterrupted()) {
            try {
                queue.addAll(transactionService.findFirst5ByStatusAndComissionGreaterThanOrderByComissionDesc(TransactionStatus.VERIFIED, BigDecimal.ZERO));
            } catch (NullPointerException e) {
                log.info("No transactions with comission...");
            }
            if (queue.size() == 5) {
                Block block = blockService.packageTransactionsInBlock(queue);
                blockService.saveBlockInDB(block, walletId);
            } else {
                try {
                    List<Transaction> transactions =
                            transactionService.getNeededNumberOfVerifiedTransactionsWithoutComission(TransactionStatus.VERIFIED, 5 - queue
                                    .size());
                    transactions.forEach(queue::offer);
                } catch (NullPointerException e) {
                    log.info("No transactions...");
                }
                if (queue.size() == 5) {
                    Block block = blockService.packageTransactionsInBlock(queue);
                    blockService.saveBlockInDB(block, walletId);
                } else {
                    log.warning("[No needed number of transactions for creating block!]");
                    transactionService.rollbackTransactionsFromBlock(queue);
                }
            }
            queue.clear();
            timer(walletId);
        }
    }

    public static void timer(String walletId) throws InterruptedException {
        log.info("[Waiting 1 minute...]");
        Thread.sleep(60_000);
        log.info("[Begin generating block!]");
    }
}
