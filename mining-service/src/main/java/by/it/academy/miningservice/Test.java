package by.it.academy.miningservice;

import by.it.academy.miningservice.RSA.RSAGenUtil;
import by.it.academy.miningservice.entity.Transaction;
import by.it.academy.miningservice.entity.TransactionStatus;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class Test {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        System.out.println(RSAGenUtil.hashWithNonce("0baf6d736b0e1393ad3333e8b2523b659b1489721d0922a78284080180e8fc6d",
                "3cb95a03-a96f-4dd7-8efe-eec4960e042f"));
    }
}
