package by.it.academy.blockchain.RSA;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

@Data
@Component
public class FileWriterUtil {

    public final String warning = "!!!!!!!ЭТО ВАШ ПРИВАТНЫЙ КЛЮЧ!!!!!!! \n !!!!!!!НИКОМУ ЕГО НЕ ДАВАЙТЕ И НЕ ТЕРЯЙТЕ, ИНАЧЕ ПОТЕРЯЕТЕ ВСЕ ДЕНЬГИ!!!!!!! \n";

    public void writeKeyToFile(String userEmail, String key) {
        Path targetPath = Paths.get("C:\\Users\\Vladislav\\Desktop\\");
        try {
            if (Files.notExists(targetPath.resolve("Wallet " + userEmail))) {
                Files.createDirectory(targetPath.resolve("Wallet " + userEmail));
            }
            if (Files.notExists(targetPath.resolve("Wallet " + userEmail + "\\secretKey.txt")) &&
                    Files.notExists(targetPath.resolve("Wallet " + userEmail + "\\README.txt"))) {
                Files.createFile(targetPath.resolve("Wallet " + userEmail + "\\secretKey.txt"));
                Files.createFile(targetPath.resolve("Wallet " + userEmail + "\\README.txt"));
            }
            Files.writeString(targetPath.resolve("Wallet " + userEmail + "\\secretKey.txt"), key);
            Files.writeString(targetPath.resolve("Wallet " + userEmail + "\\README.txt"), warning);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
