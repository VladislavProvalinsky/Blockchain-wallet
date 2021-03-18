package by.it.academy.blockchain.service;

import by.it.academy.blockchain.RSA.BCKeysFactoryUtil;
import by.it.academy.blockchain.RSA.FileWriterUtil;
import by.it.academy.blockchain.RSA.RSAGenUtil;
import by.it.academy.blockchain.entity.Input;
import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.security.KeyPair;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
public class WalletService {

    private static final Logger log = Logger.getLogger(WalletService.class.getName());

    @Autowired
    EntityManager entityManager;

    @Autowired
    WalletRepository walletRepository;

    public Wallet getOne(String walletId) {
        Wallet wallet = null;
        try {
            wallet = (Wallet) entityManager.createQuery("From Wallet where id=:id")
                    .setParameter("id", walletId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return wallet;
    }

    @Transactional
    public void updateWallet(Wallet wallet) {
        walletRepository.save(wallet);
    }

    public Wallet registerNewWalletUtil(KeyPair keyPair) {
        String publicKey = RSAGenUtil.stringPublicKey(keyPair.getPublic());
        // создаем новый кошелек и сетим в него первый инпут и баланс
        Wallet wallet = new Wallet(publicKey);
        wallet.getInputs().add(new Input(BigDecimal.valueOf(10)));
        return wallet;
    }



    public Double getActualBalance(Wallet wallet) {
        double inputsSum = wallet
                .getInputs()
                .stream()
                .flatMapToDouble(e -> DoubleStream.of(e.getValue().doubleValue()))
                .sum();
        double outputsSum = wallet
                .getOutputs()
                .stream()
                .flatMapToDouble(e -> DoubleStream.of(e.getValue().doubleValue()))
                .sum();
        return inputsSum - outputsSum;
    }

    public Page<Wallet> getAllWallets(Pageable pageable) {
        return walletRepository.findAll(pageable);
    }
}
