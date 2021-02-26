package by.it.academy.miningservice.service;

import by.it.academy.miningservice.RSA.RSAGenUtil;
import by.it.academy.miningservice.entity.Block;
import by.it.academy.miningservice.entity.Transaction;
import by.it.academy.miningservice.entity.TransactionStatus;
import by.it.academy.miningservice.generator.BlockHashGenerator;
import by.it.academy.miningservice.repository.BlockRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Log
@Service
public class BlockService {

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    EntityManager entityManager;

    public Block getLastBlockFromDB() throws NoResultException {
        TypedQuery<Block> query = entityManager.createQuery("FROM Block B ORDER BY B.date DESC", Block.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    public Block packageTransactionsInBlock(Queue<Transaction> transactions) {
        List<Transaction> transactionList = new ArrayList<>(transactions);
        Block lastBlockFromDB = getLastBlockFromDB();
        Block block = new Block();
        if (lastBlockFromDB.getId() == null) {
            block.setTransactions(transactionList);
            block.getTransactions().forEach(tx-> tx.setStatus(TransactionStatus.IN_BLOCK));
            block.setDate(LocalDateTime.now());
            block.setPreviousHash("Genesis-Block");
            block.setNonce(BlockHashGenerator.generateNonceForNewBlock(block));
            block.setHash(RSAGenUtil.hashWithNonce(RSAGenUtil.hashBlockData(block), block.getNonce()));
            return block;
        } else {
            block.setTransactions(transactionList);
            block.getTransactions().forEach(tx-> tx.setStatus(TransactionStatus.IN_BLOCK));
            block.setDate(LocalDateTime.now());
            block.setPreviousHash(lastBlockFromDB.getHash());
            block.setNonce(BlockHashGenerator.generateNonceForNewBlock(block));
            block.setHash(RSAGenUtil.hashWithNonce(RSAGenUtil.hashBlockData(block), block.getNonce()));
            return block;
        }
    }

    public void saveBlockInDB (Block block){
        blockRepository.save(block);
    }




}
