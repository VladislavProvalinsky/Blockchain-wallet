package by.it.academy.blockchain.RSA;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSAGen {

    public static String generateRandomPrivateKey () throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("RSA");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();
        String key = Base64.encodeBase64String(secretKey.getEncoded());
        return key;
    }

    public static PublicKey getPublicKey (PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec priv = kf.getKeySpec(privateKey, RSAPrivateKeySpec.class);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(priv.getModulus(), BigInteger.valueOf(65537));
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
    }

    public static RSAPublicKeySpec getPublicKeySpec(PrivateKey priv) {
        RSAPrivateCrtKey rsaCrtKey = (RSAPrivateCrtKey) priv; // May throw a ClassCastException
        return new RSAPublicKeySpec(rsaCrtKey.getModulus(), rsaCrtKey.getPublicExponent());
    }

}
