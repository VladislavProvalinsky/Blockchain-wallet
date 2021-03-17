package by.it.academy.blockchain.RSA;

import lombok.Data;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.*;

@Data
@Component
public class FileWriterUtil {

    public final String warning = "!!!!!!!THIS IS YOUR PRIVATE KEY!!!!!!! \n !!!!!!!DON'T MISS IT!!!!!!! \n";
    public final String fileSeparator = FileSystems.getDefault().getSeparator();

    public void writeKeyToFile(String userEmail, String key) {
        Path targetPath = Paths.get("C:" + fileSeparator);
        Path blockchainWalletDir = targetPath.resolve("Blockchain Wallet");
        Path userDir = null;
        try {
            if (Files.notExists(targetPath.resolve("Blockchain Wallet"))) {
                blockchainWalletDir = Files.createDirectory(targetPath.resolve("Blockchain Wallet"));
            }
            if (Files.notExists(Path.of(blockchainWalletDir + fileSeparator + userEmail + fileSeparator + "secretKey.txt")) &&
                    Files.notExists(Path.of(blockchainWalletDir + fileSeparator + userEmail + fileSeparator + "README.txt"))) {
                userDir = Files.createDirectory(Path.of(blockchainWalletDir + fileSeparator + userEmail));
                Files.createFile(Path.of(userDir + fileSeparator + "secretKey.txt"));
                Files.createFile(Path.of(userDir + fileSeparator + "README.txt"));
            }
            Files.writeString(Path.of(userDir + fileSeparator + "secretKey.txt"), key);
            Files.writeString(Path.of(userDir + fileSeparator + "README.txt"), warning);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
