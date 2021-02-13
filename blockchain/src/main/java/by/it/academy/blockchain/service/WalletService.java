package by.it.academy.blockchain.service;

import by.it.academy.blockchain.RSA.BCKeysFactoryUtil;
import by.it.academy.blockchain.RSA.FileWriterUtil;
import by.it.academy.blockchain.RSA.RSAGenUtil;
import by.it.academy.blockchain.entity.Input;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.repository.WalletRepository;
import org.bouncycastle.jcajce.provider.asymmetric.RSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.security.KeyPair;
import java.util.logging.Logger;
import java.util.stream.DoubleStream;

@Service
public class WalletService {

    private static final Logger log = Logger.getLogger(WalletService.class.getName());

    @Autowired
    EntityManager entityManager;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    BCKeysFactoryUtil BCKeysFactoryUtil;

    @Autowired
    FileWriterUtil fileWriterUtil;

    @Transactional
    public Wallet getOneByUserId(Long id) {
        Wallet wallet = null;
        try {
            wallet = (Wallet) entityManager.createQuery("From Wallet where user_id=:id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return wallet;
    }

    @Transactional
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
        entityManager.merge(wallet);
    }

    public Wallet registerNewWalletUtil(User user) {
        KeyPair keyPair = RSAGenUtil.generateKeyPair();
        String privateKey = RSAGenUtil.stringPrivateKey(keyPair.getPrivate());
        String publicKey = RSAGenUtil.stringPublicKey(keyPair.getPublic());
        fileWriterUtil.writeKeyToFile(user.getUsername(), privateKey);
        // создаем новый кошелек и сетим в него первый инпут и баланс
        Wallet wallet = new Wallet(publicKey);
        wallet.getInputs().add(new Input(BigDecimal.valueOf(10)));
        return wallet;
    }

    public Double getActualBalance(Wallet wallet) {
        double inputsSum = wallet.getInputs().stream().flatMapToDouble(e -> DoubleStream.of(e.getValue().doubleValue())).sum();
        double outputsSum = wallet.getOutputs().stream().flatMapToDouble(e -> DoubleStream.of(e.getValue().doubleValue())).sum();
        return inputsSum - outputsSum;
    }
}
