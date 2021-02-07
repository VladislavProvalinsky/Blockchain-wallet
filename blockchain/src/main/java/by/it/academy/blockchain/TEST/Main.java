package by.it.academy.blockchain.TEST;

import by.it.academy.blockchain.RSA.AssimetricUtil;
import by.it.academy.blockchain.RSA.FileWriterUtil;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        AssimetricUtil assimetricUtil = new AssimetricUtil();
        FileWriterUtil fileWriterUtil = new FileWriterUtil();

        try {
            KeyPair keyPair = assimetricUtil.generateKeyPair();
            String publicKey = assimetricUtil.encodePublicKey(keyPair.getPublic());
            String privateKey = assimetricUtil.encodePrivateKey(keyPair.getPrivate());
            System.out.println("Public key!!! " + publicKey);
            System.out.println("Private key!!! " + privateKey);
            fileWriterUtil.writeKeyToFile("vpr@mail.ru", privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
