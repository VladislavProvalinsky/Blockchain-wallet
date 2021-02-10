package by.it.academy.blockchain.service;

import by.it.academy.blockchain.RSA.BCKeysFactoryUtil;
import by.it.academy.blockchain.RSA.FileWriterUtil;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.security.KeyPair;

@Service
public class WalletService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    BCKeysFactoryUtil BCKeysFactoryUtil;

    @Autowired
    FileWriterUtil fileWriterUtil;

    public Wallet getOne(Long id) {
        return (Wallet) entityManager.createQuery("From Wallet where user_id=:id")
                .setParameter("id", id)
                .getSingleResult();

    }

    public Wallet registerNewWalletUtil(User user) {
        KeyPair keyPair = BCKeysFactoryUtil.generateKeyPair();
        String publicKey = BCKeysFactoryUtil.encodePublicKey(keyPair.getPublic());
        String privateKey = BCKeysFactoryUtil.encodePrivateKey(keyPair.getPrivate());
        fileWriterUtil.writeKeyToFile(user.getUsername(), privateKey);
        return new Wallet(publicKey, 10.0);
    }
}
