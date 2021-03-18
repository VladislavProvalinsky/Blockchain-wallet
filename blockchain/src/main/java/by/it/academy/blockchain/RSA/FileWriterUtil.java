package by.it.academy.blockchain.RSA;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@Data
@Component
public class FileWriterUtil {

    public final String warning = "!!!!!!!THIS IS YOUR PRIVATE KEY!!!!!!! \n !!!!!!!DON'T MISS IT!!!!!!! \n";
    public final String fileSeparator = FileSystems.getDefault().getSeparator();

    public Path writeKeyToFile(String userEmail, String privateKey) {
        Path targetRootPath = FileSystems.getDefault().getRootDirectories().iterator().next();
        Path blockchainWalletDir = targetRootPath.resolve("Blockchain Wallet");
        Path userDir = blockchainWalletDir.resolve(userEmail);
        try {
            if (Files.notExists(targetRootPath.resolve("Blockchain Wallet"))) {
                blockchainWalletDir = Files.createDirectory(targetRootPath.resolve("Blockchain Wallet"));
            }
            if (Files.notExists(Path.of(blockchainWalletDir + fileSeparator + userEmail + fileSeparator + "secretKey.txt")) &&
                    Files.notExists(Path.of(blockchainWalletDir + fileSeparator + userEmail + fileSeparator + "README.txt"))) {
                userDir = Files.createDirectory(Path.of(blockchainWalletDir + fileSeparator + userEmail));
                Files.createFile(Path.of(userDir + fileSeparator + "secretKey.txt"));
                Files.createFile(Path.of(userDir + fileSeparator + "README.txt"));
            }
            Files.writeString(Path.of(userDir + fileSeparator + "secretKey.txt"), privateKey);
            Files.writeString(Path.of(userDir + fileSeparator + "README.txt"), warning);
            return userDir;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userDir;
    }
}
