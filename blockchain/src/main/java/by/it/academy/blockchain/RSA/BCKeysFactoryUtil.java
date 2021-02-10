package by.it.academy.blockchain.RSA;

import by.it.academy.blockchain.entity.Transaction;
import org.bitcoinj.core.Base58;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.jcajce.provider.digest.SHA512;
import org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;
import java.util.Base64;

@Component
public class BCKeysFactoryUtil {

    public KeyPair generateKeyPair() {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
            keyGen.initialize(ecSpec);
            keyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    public String getBCPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        ECPrivateKey epvt = (ECPrivateKey) privateKey;
        String sepvt = adjustTo64(epvt.getS().toString(16));
        return sepvt;
    }

    public String getBCPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        ECPublicKey epub = (ECPublicKey) publicKey;
        ECPoint pt = epub.getW();
        String sx = adjustTo64(pt.getAffineX().toString(16)).toUpperCase();
        String sy = adjustTo64(pt.getAffineY().toString(16)).toUpperCase();
        String bcPub = "04" + sx + sy;
        return bcPub;
    }

    public String computeBCPubKeyInSHA256(String publicKey) {
        // register the BouncyCastleProvider with the Security Manager
        Security.addProvider(new BouncyCastleProvider());
        String targetBCPublicKey = null;
        try {
            // perform a SHA-256 digest on the public key, followed by a RIPEMD-160 digest
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] s1 = sha.digest(hexStringToByteArray(publicKey));
            // use the Bouncy Castle provider for performing the RIPEMD-160 digest since JCE does not implement this algorithm
            MessageDigest rmd = MessageDigest.getInstance("RipeMD160", "BC");
            byte[] r1 = rmd.digest(s1);
            // add a version byte of 0x00 at the beginning of the hash.
            byte[] r2 = new byte[r1.length + 1];
            r2[0] = 0;
            for (int i = 0; i < r1.length; i++) r2[i + 1] = r1[i];
            // Repeat the SHA-256 Hashing Twice
            byte[] s2 = sha.digest(r2);
            byte[] s3 = sha.digest(s2);
            // The first 4 bytes of the result of the second hashing is used as the address checksum.
            // It is appended to the RIPEMD160 hash above. This is the 25-byte bitcoin address.
            byte[] a1 = new byte[25];
            for (int i = 0; i < r2.length; i++) a1[i] = r2[i];
            for (int i = 0; i < 4; i++) a1[20 + i] = s3[i];
            // use the Base58.encode() method from the bitcoinj library to arrive at the final bitcoin address.
            // This is the address to which the bitcoin should be sent to in a transaction.
            targetBCPublicKey = Base58.encode(a1);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return targetBCPublicKey;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    static private String adjustTo64(String s) {
        switch (s.length()) {
            case 62:
                return "00" + s;
            case 63:
                return "0" + s;
            case 64:
                return s;
            default:
                throw new IllegalArgumentException("not a valid key: " + s);
        }
    }

    // -----------------

    public boolean verifyTransaction (String publicKey, String transactionHash){

    }

    // хешируем транзакцию с помощью хеш функции
    public String hashTransaction (Transaction transaction){
        SHA512Digest digest = new SHA512Digest();

    }


    public String encodePublicKey(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public String encodePrivateKey(PrivateKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public PublicKey decodePublicKey(String publicKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decode = Base58.decode(publicKey);
        byte[] keyBytes = Base64.getDecoder().decode(decode);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(spec);
        return key;
    }

    public PrivateKey decodePrivateKey(String privateKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey.getBytes(StandardCharsets.UTF_8));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PrivateKey priv = fact.generatePrivate(keySpec);
        return priv;
    }

    // ------------------------

    public byte[] encrypt(PrivateKey privateKey, String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(data.getBytes());
        return result;
    }

    public String decrypt(PublicKey publicKey, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(data);
        String res = new String(result);
        return res;
    }
}
