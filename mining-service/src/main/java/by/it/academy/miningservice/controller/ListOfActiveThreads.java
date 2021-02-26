package by.it.academy.miningservice.controller;

import by.it.academy.miningservice.main.BlockGenThread;
import by.it.academy.miningservice.main.TXVerificationThread;
import lombok.Data;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Optional;

@Data
@Log
public class ListOfActiveThreads {

    private static List<TXVerificationThread> txVerificationThreads;

    private static List<BlockGenThread> blockGenThreads;

    public static void addElementsInProcess(TXVerificationThread verificationThread, BlockGenThread blockGenThread) {
        txVerificationThreads.add(verificationThread);
        blockGenThreads.add(blockGenThread);
    }

    public static void removeElementsFromStoppedProcess(String walletId) {
        txVerificationThreads.remove(txVerificationThreads.stream().filter(e->e.getWalletId().equals(walletId)).findFirst().get());
        blockGenThreads.remove(blockGenThreads.stream().filter(e->e.getWalletId().equals(walletId)).findFirst().get());
    }

    public static void startProcess(TXVerificationThread verificationThread, BlockGenThread blockGenThread) {

    }

    public static void stopProcess(String walletId) {
        Optional<TXVerificationThread> first = txVerificationThreads.stream().filter(e -> e.getWalletId().equals(walletId)).findFirst();
        Optional<BlockGenThread> first1 = blockGenThreads.stream().filter(e -> e.getWalletId().equals(walletId)).findFirst();
        if (first.isPresent() && first1.isPresent()) {
            TXVerificationThread thread = first.get();
            BlockGenThread genThread = first1.get();
            thread.setStopFlag(true);
            genThread.setStopFlag(true);
        } else {
            log.info("No such process!");
        }
    }


}
