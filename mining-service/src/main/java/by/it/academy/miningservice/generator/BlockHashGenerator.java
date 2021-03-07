package by.it.academy.miningservice.generator;

import by.it.academy.miningservice.RSA.RSAGenUtil;
import by.it.academy.miningservice.entity.Block;

import java.util.UUID;

public class BlockHashGenerator {

    public static String generateNonceForNewBlock(Block block) {
        String resultHash = "";
        String nonce = "";
        String hashData = RSAGenUtil.hashBlockData(block);
        while (!resultHash.startsWith("00000")) {
            nonce = UUID.randomUUID().toString();
            resultHash = RSAGenUtil.hashWithNonce(hashData, nonce);
        }
        return nonce;
    }
}
