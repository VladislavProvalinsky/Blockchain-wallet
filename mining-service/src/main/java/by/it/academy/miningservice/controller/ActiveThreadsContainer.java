package by.it.academy.miningservice.controller;

import by.it.academy.miningservice.main.BlockGenThread;
import by.it.academy.miningservice.main.TXVerificationThread;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Log
public class ActiveThreadsContainer {

    private static List<TXVerificationThread> txVerificationThreads = new ArrayList<>();
    private static List<BlockGenThread> blockGenThreads = new ArrayList<>();

    public static void addElementsInProcess(TXVerificationThread verificationThread, BlockGenThread blockGenThread) {
        txVerificationThreads.add(verificationThread);
        blockGenThreads.add(blockGenThread);
    }

    public static void startProcess(TXVerificationThread verificationThread, BlockGenThread blockGenThread) {
        verificationThread.start();
        blockGenThread.start();
    }

    public static void stopProcess(String walletId) {
        Optional<TXVerificationThread> first = txVerificationThreads
                .stream()
                .filter(e -> e.getWalletId().equals(walletId))
                .findFirst();
        Optional<BlockGenThread> first1 = blockGenThreads
                .stream()
                .filter(e -> e.getWalletId().equals(walletId))
                .findFirst();
        if (first.isPresent() && first1.isPresent()) {
            TXVerificationThread thread = first.get();
            BlockGenThread genThread = first1.get();
            thread.interrupt();
            genThread.interrupt();
        } else {
            log.info("No such process!");
        }
    }


    public static class ContainerGC extends Thread {

        @SneakyThrows
        @Override
        public void run() {
            while (!this.isInterrupted()) {
                log.info("ContainerGC starting clearing container...");
                List<TXVerificationThread> txThreads = ActiveThreadsContainer.txVerificationThreads
                        .stream()
                        .filter(el -> !el.isAlive())
                        .collect(Collectors.toList());
                List<BlockGenThread> blockThreads = ActiveThreadsContainer.blockGenThreads
                        .stream()
                        .filter(el -> !el.isAlive())
                        .collect(Collectors.toList());
                if (!txThreads.isEmpty()) {
                    log.info("Cleaning " + txThreads + " from container..." );
                    txVerificationThreads.removeAll(txThreads);
                }
                if (!blockThreads.isEmpty()) {
                    log.info("Cleaning " + blockThreads + " from container..." );
                    blockGenThreads.removeAll(blockThreads);
                }
                log.info("ContainerGC waiting 5 minutes...");
                Thread.sleep(300_000);
            }
        }
    }


}
