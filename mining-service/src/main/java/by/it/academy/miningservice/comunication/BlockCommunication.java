package by.it.academy.miningservice.comunication;

import by.it.academy.miningservice.dto.Block;
import by.it.academy.miningservice.dto.Transaction;
import by.it.academy.miningservice.generator.BlockHashGenerator;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Log
public class BlockCommunication {

    public static final String BlockURL = "http://localhost:8080/blockchain/blocks";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TransactionCommunication transactionCommunication;


    public void packageTransactionsInBlock(List<Transaction> transactions) {
        if (checkThatListContains5Transactions(transactions)) return;
        Block lastBlockFromDB = getLastBlockFromDB();
        if (lastBlockFromDB.getId() == null) {
            lastBlockFromDB.setTransactions(transactions);
            lastBlockFromDB.setDate(LocalDateTime.now());
            lastBlockFromDB.setPreviousHash("Genesis Block");
            lastBlockFromDB.setHash(BlockHashGenerator.generateNewHashForNewBlock(lastBlockFromDB));
            // надо всем транзакциям поменять статусы
        }


    }


    public Block getLastBlockFromDB() {
        try {
            return restTemplate.getForObject(BlockURL + "/getLast", Block.class);
        } catch (Exception e) {
            log.warning("No Blocks in DB! Creating new one...");
            return new Block();
        }
    }

//    private boolean checkThatDBHasBlocks() {
//        restTemplate.getForObject()
//    }

    public boolean checkThatListContains5Transactions(List<Transaction> transactions) {
        return transactions.size() == 5;
    }

}
