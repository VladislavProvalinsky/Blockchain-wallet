package by.it.academy.miningservice.main;

import by.it.academy.miningservice.RSA.RSAGenUtil;
import by.it.academy.miningservice.entity.Block;
import by.it.academy.miningservice.entity.Transaction;
import by.it.academy.miningservice.entity.TransactionStatus;
import by.it.academy.miningservice.handleException.TransactionHashException;
import by.it.academy.miningservice.repository.BlockRepository;
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
public class BlockVerificationThread extends Thread {

    @Autowired
    private BlockRepository blockRepository;

    @SneakyThrows
    @Override
    public void run() {
        while (!this.isInterrupted()) {
            List<Block> blockTree = blockRepository.findAll();
            if (blockTree.isEmpty()) log.info("[Block Tree in DB is empty. Waiting for new ones...]");
            else {
                try {
                    blockTree.forEach(RSAGenUtil::checkBlockHash);
                } catch (TransactionHashException e) {
                    log.warning(e.getMessage());
                    System.exit(-1);
                }
            }
            timer();
        }
    }

    public static void timer() throws InterruptedException {
        log.info("[Waiting 5 minutes...]");
        Thread.sleep(300_000);
        log.info("[Begining verification block tree!]");
    }
}
