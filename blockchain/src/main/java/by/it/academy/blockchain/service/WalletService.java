package by.it.academy.blockchain.service;

import by.it.academy.blockchain.RSA.BCKeysFactoryUtil;
import by.it.academy.blockchain.RSA.FileWriterUtil;
import by.it.academy.blockchain.RSA.RSAGenUtil;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.repository.WalletRepository;
import org.bouncycastle.jcajce.provider.asymmetric.RSA;
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

    public Wallet getOne(String walletId) {
        return (Wallet) entityManager.createQuery("From Wallet where id=:id")
                .setParameter("id", walletId)
                .getSingleResult();
    }

    public void updateWallet (Wallet wallet) {
        entityManager.merge(wallet);
    }

    public Wallet registerNewWalletUtil(User user) {
        KeyPair keyPair = RSAGenUtil.generateKeyPair();
        String privateKey = RSAGenUtil.stringPrivateKey(keyPair.getPrivate());
        String publicKey = RSAGenUtil.stringPublicKey(keyPair.getPublic());
        fileWriterUtil.writeKeyToFile(user.getUsername(), privateKey);
        return new Wallet(publicKey, 10.0);
    }
}
