package by.it.academy.miningservice.service;

import by.it.academy.miningservice.RSA.RSAGenUtil;
import by.it.academy.miningservice.entity.*;
import by.it.academy.miningservice.generator.BlockHashGenerator;
import by.it.academy.miningservice.repository.BlockRepository;
import by.it.academy.miningservice.repository.WalletRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.DoubleStream;

@Log
@Service
public class BlockService {

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    EntityManager entityManager;

    public Block getLastBlockFromDB() {
        try {
            TypedQuery<Block> query = entityManager.createQuery("FROM Block B ORDER BY B.date DESC", Block.class);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e){
            log.info("No blocks in DB");
            return null;
        }
    }

    public Block packageTransactionsInBlock(Queue<Transaction> transactions) {
        List<Transaction> transactionList = new ArrayList<>(transactions);
        Block lastBlockFromDB = getLastBlockFromDB();
        Block block = new Block();
        block.setTransactions(transactionList);
        block.setDate(LocalDateTime.now());
        if (lastBlockFromDB == null) {
            block.setPreviousHash("Genesis-Block");
        } else {
            block.setPreviousHash(lastBlockFromDB.getHash());
        }
        block.setNonce(BlockHashGenerator.generateNonceForNewBlock(block));
        block.setHash(RSAGenUtil.hashWithNonce(RSAGenUtil.hashBlockData(block), block.getNonce()));
        return block;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void saveBlockInDB(Block block, String walletId) {
        saveGonorarForMiner(block, walletId);
        transferValuesToReceivers(block);
        blockRepository.save(block);
    }

    private void saveGonorarForMiner(Block block, String walletId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.isPresent()) {
            Wallet userWallet = wallet.get();
            double sum = block
                    .getTransactions()
                    .stream()
                    .flatMapToDouble(tx -> DoubleStream.of(tx.getComission().doubleValue()))
                    .sum();
            if (sum == 0) {
                userWallet.getInputs().add(new Input(BigDecimal.valueOf(0.5)));
            } else {
                userWallet.getInputs().add(new Input(BigDecimal.valueOf(sum)));
            }
            walletRepository.save(userWallet);
        } else log.warning("No such wallet!");
    }

    private void transferValuesToReceivers(Block block) {
        block.getTransactions().forEach(tx -> {
            Wallet wallet = walletRepository.getOne(tx.getReceiverPublicKey());
            wallet.getInputs().add(new Input(tx.getValue()));
            walletRepository.save(wallet);
        });
    }


}
