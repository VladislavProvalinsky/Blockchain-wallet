package by.it.academy.miningservice.main;

import by.it.academy.miningservice.RSA.RSAGenUtil;
import by.it.academy.miningservice.entity.Block;
import by.it.academy.miningservice.handleException.TransactionHashException;
import by.it.academy.miningservice.service.BlockService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log
@Data
@EqualsAndHashCode(callSuper = true)
@Component
public class BlockVerificationThread extends Thread {

    @Autowired
    private BlockService blockService;

    @SneakyThrows
    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                List<Block> blockTree = blockService.getAllBlocks();
                try {
                    blockTree.forEach(RSAGenUtil::checkBlockHash);
                    log.info("[Block Tree was checked successfully for security by system. Waiting 5 minutes...]");
                } catch (TransactionHashException e) {
                    log.warning(e.getMessage());
                    System.exit(-1);
                }
            } catch (NullPointerException e) {
                log.info("[Block Tree in DB is empty. Waiting for new ones...]");
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
