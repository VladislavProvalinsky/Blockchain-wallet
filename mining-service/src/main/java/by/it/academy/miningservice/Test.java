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

        Transaction transaction = new Transaction();
        transaction.setSenderPublicKey("rbanryFCC6yJnCRCQgVWyYXvqkSBz4k8KgwF33RVMPYZWRh5tM1E5Nkx9JM27BrSXuTmpuLaShcuMKTBTmfmd4kdyZhghiLN5aMezgCgk5421giC9F365dsJPfzV6Dap");
        transaction.setReceiverPublicKey("rbanryFCC6yJnCRCQgVWyYXvqkSBz4k8KgnEkNRnDR4iVESsKGV6ccaZ6gpt8UznfAkw6HE22TvZVBikikDUa5ebavFZBb1a5bNQqsU22k9CKRCvivd2WNM7o7fcKtjJ");
        transaction.setValue(BigDecimal.TEN);
        transaction.setComission(BigDecimal.ONE);
        transaction.setStatus(TransactionStatus.NOT_CONFIRMED);
        transaction.setSignature("2YxGPrDW2hwdAfrXymm5pJb1ftXzKmPqYBqYdgQWAcBRzMk8nDY6oPoLU8jTng2tNyofDycu9VmZMam32ZwZTzrjuqaEqwAc4E3dqWSzakRLzLHmdnDwK1yTfMThn5KqThRsPvhDZ5HPppjz5bSknvjvXAbcPxRf2FEavTpHHjuzBvguSnFYjgC6WaGuwT8VUr2oV7WqkuD7Zi7pUDTgWYFDQFoeatbVtKTp1szabwjA9gCbzunJEpC8Y17VcGiKXk3k9hT7GQtp2auKfNMZLXRiTHZf8uPTxwLEGw9FyaN2fvXKoLjQMdNFHMKQiRe59PYsGtqgnmeq4XeHVMDuHZdUwzxoo1VTET6UJctJazoxZRuPtLDYqoLYDCErzygQbSvHveCoX4gtSbt3UBQFpXq4LnMCHbhVUv2ZYwa8CGT3TfapstsRV24sihxRmrrRVgWGGP9k851ZqbwW7Vb9asCNX");
        transaction.setDate(LocalDateTime.now());
        transaction.setId(RSAGenUtil.hashTransaction(transaction));

        System.out.println(RSAGenUtil.checkTransactionHash(transaction));
        transaction.setSenderPublicKey("rbanryFCC6yJnCRCQgVWyYXvqkSBz4k8KgwF33RVMPYZWRh5tM1E5Nkx9JM27BrSXuTmpuLaShcuMKTBTmfmd4kdyZhghiLN5aMezgCgk5421giC9F365dsJPfzV6Dap");
        System.out.println(RSAGenUtil.checkTransactionHash(transaction));
        System.out.println(transaction);
    }
}
