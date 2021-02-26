package by.it.academy.miningservice;

import by.it.academy.miningservice.RSA.RSAGenUtil;
import by.it.academy.miningservice.entity.Block;
import org.bitcoinj.core.Base58;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Test {

    public static String generateNewHashForNewBlock(String string) throws NoSuchAlgorithmException {
        String resultHash = "";
        String nonce = "";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                string.getBytes(StandardCharsets.UTF_8));
        String sha256hex = new String(Hex.encode(hash));
        System.out.println(sha256hex);
        while (!resultHash.startsWith("000000")){
            nonce = UUID.randomUUID().toString();
            resultHash = RSAGenUtil.hashWithNonce(string, nonce);
        }
        return nonce;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        System.out.println(generateNewHashForNewBlock("Вастрик"));


    }
}
