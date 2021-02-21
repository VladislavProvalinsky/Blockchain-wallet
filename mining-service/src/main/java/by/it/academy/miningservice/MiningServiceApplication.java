package by.it.academy.miningservice;

import by.it.academy.miningservice.comunication.TransactionCommunication;
import by.it.academy.miningservice.dto.Transaction;
import by.it.academy.miningservice.dto.TransactionStatus;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@Log
public class MiningServiceApplication {

    public static volatile boolean flag;

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(MiningServiceApplication.class, args);

        TransactionCommunication transactionCommunication =
                context.getBean(TransactionCommunication.class);

        Thread verificationThread = new Thread(new Runnable() {
            @Override
            public void run() {
//                if (flag) return;
                log.info("Starting verifying...");
                List<Transaction> first5NotConfirmedTransactions =
                        transactionCommunication.getFirst5NotConfirmedTransactions(TransactionStatus.NOT_CONFIRMED);
                if (first5NotConfirmedTransactions.isEmpty()) {
                    log.info("No transactions for verifying. Waiting...");
                } else {
                    first5NotConfirmedTransactions.forEach(System.out::println);
                    transactionCommunication.putCheckedTransactionsInDB(first5NotConfirmedTransactions);
                    log.info("Waiting 1 minute....");
                }
                timer();
                run();
            }
        });

        Scanner exitFromMiner = new Scanner(System.in);
        System.out.println("Welcome to Miner!\n " +
                "For stopping app write 'STOP-APP' \n " +
                "For stopping verifying write 'STOP-V'\n " +
                "Good Luck!!");
        verificationThread.start();
        exit(exitFromMiner, verificationThread);

    }

    public static String exit(Scanner scanner, Thread verificationThread) {
        String exit = scanner.nextLine();
        if (exit.equals("STOP")) System.exit(0);
        if (exit.equals("STOP-V")) verificationThread.interrupt();
        return exit(scanner, verificationThread);
    }

    @SneakyThrows
    public static void timer() {
        Thread.sleep(60_000);
//        for (int i = 60; i >= 0; i--) {
//            System.out.println("Осталось: " + ((i > 4) ? i + " секунд" : (i > 1) ? i + " секунды" : (i == 1) ? i + " секунда" : "менее секунды"));
//            Thread.sleep(1000L);
//        }
        System.out.println("Начинаем верификацию транзакций!");
    }



}

