package by.it.academy.miningservice.RSA;

import by.it.academy.miningservice.entity.Block;
import by.it.academy.miningservice.entity.Transaction;
import by.it.academy.miningservice.handleException.TransactionHashException;
import org.bitcoinj.core.Base58;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.format.DateTimeFormatter;

public class RSAGenUtil {

    public static KeyPair generateKeyPair() {
        KeyPair kp = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(512);
            kp = kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return kp;
    }

    public static PrivateKey getPrivate(KeyPair keyPair) {
        PrivateKey pvt = null;
        try {
            PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
            KeyFactory kf = KeyFactory.getInstance("RSA");
            pvt = kf.generatePrivate(ks);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return pvt;
    }

    public static PublicKey getPublic(KeyPair keyPair) {
        PublicKey pub = null;
        try {
            X509EncodedKeySpec ks = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
            KeyFactory kf = KeyFactory.getInstance("RSA");
            pub = kf.generatePublic(ks);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return pub;
    }

    public static String stringPublicKey(PublicKey publicKey) {
        Base58.encode(publicKey.getEncoded());
        return Base58.encode(publicKey.getEncoded());
    }

    public static String stringPrivateKey(PrivateKey privateKey) {
        Base58.encode(privateKey.getEncoded());
        return Base58.encode(privateKey.getEncoded());
    }

    public static PublicKey getPublicFromString(String publicKey) {
        PublicKey pub = null;
        try {
            X509EncodedKeySpec ks = new X509EncodedKeySpec(Base58.decode(publicKey));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            pub = kf.generatePublic(ks);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return pub;
    }

    public static PrivateKey getPrivateFromString(String privateKey) {
        PrivateKey pvt = null;
        try {
            PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(Base58.decode(privateKey));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            pvt = kf.generatePrivate(ks);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return pvt;
    }

    public static String getSignature(PrivateKey privateKey, String data) {
        String signData = null;
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(privateKey);
            sign.update(data.getBytes());
            signData = Base58.encode(sign.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return signData;
    }

    public static boolean verifySignature(PublicKey publicKey, String data, String signature) {
        boolean result = false;
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(publicKey);
            sign.update(data.getBytes());
            result = sign.verify(Base58.decode(signature));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String hashTransaction(Transaction transaction) {
        String txDataToHash = transaction.getSenderPublicKey() +
                transaction.getReceiverPublicKey() +
                transaction.getValue() +
                transaction.getComission() +
                transaction.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        String txId = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(txDataToHash.getBytes());
            txId = Base58.encode(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return txId;
    }

    public static String hashBlockData(Block block) {
        String blockDataToHash = block.getTransactions().toString() +
                block.getPreviousHash() + block.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        ;
        String blockDataHash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(blockDataToHash.getBytes(StandardCharsets.UTF_8));
            blockDataHash = new String(Hex.encode(hash));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return blockDataHash;
    }


    public static String hashWithNonce(String hashData, String nonce) {
        String sumData = hashData + nonce;
        String sumHash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(sumData.getBytes(StandardCharsets.UTF_8));
            sumHash = new String(Hex.encode(hash));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sumHash;
    }


    public static boolean checkTransactionHash(Transaction transaction) {
        String txId = hashTransaction(transaction);
        if (txId.equals(transaction.getId())) {
            return true;
        } else
            throw new TransactionHashException("Hashes are different! Transaction with id = " + transaction.getId() + " was modified!!!");
    }

    public static boolean checkBlockHash(Block block) {
        String sourceHash = RSAGenUtil.hashWithNonce(RSAGenUtil.hashBlockData(block), block.getNonce());
        if (sourceHash.equals(block.getHash())) {
            return true;
        } else
            throw new TransactionHashException("Hashes are different! Block with id = " + block.getId() + " was modified!!!\n Force Stopping Server!");
    }

}
