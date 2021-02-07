package by.it.academy.blockchain.service;

import by.it.academy.blockchain.RSA.AssimetricUtil;
import by.it.academy.blockchain.RSA.FileWriterUtil;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

@Service
public class WalletService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    AssimetricUtil assimetricUtil;

    @Autowired
    FileWriterUtil fileWriterUtil;

    public Wallet getOne (Long id){
        return (Wallet) entityManager.createQuery("From Wallet where user_id=:id")
        .setParameter("id", id)
        .getSingleResult();

    }

    public Wallet registerNewWalletUtil (User user) {
        String publicKey = null;
        String privateKey = null;
        try {
            KeyPair keyPair = assimetricUtil.generateKeyPair();
            publicKey = assimetricUtil.encodePublicKey(keyPair.getPublic());
            privateKey = assimetricUtil.encodePrivateKey(keyPair.getPrivate());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        fileWriterUtil.writeKeyToFile(user.getUsername(), privateKey);
        return new Wallet(publicKey, 10.0);
    }
}
