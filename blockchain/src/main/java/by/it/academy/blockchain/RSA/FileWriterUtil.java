package by.it.academy.blockchain.RSA;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Component
public class FileWriterUtil {

    public final String warning = "!!!!!!!ЭТО ВАШ ПРИВАТНЫЙ КЛЮЧ!!!!!!! \n !!!!!!!НИКОМУ ЕГО НЕ ДАВАЙТЕ И НЕ ТЕРЯЙТЕ, ИНАЧЕ ПОТЕРЯЕТЕ ВСЕ ДЕНЬГИ!!!!!!! \n";

    public void writeKeyToFile(String userEmail, String key) {
        Path targetPath = Paths.get("C:\\Users\\Vladislav\\Desktop\\");
        try {
            Files.createDirectory(targetPath.resolve("Wallet " + userEmail));
            Files.createFile(targetPath.resolve("Wallet " + userEmail +"\\secretKey.txt"));
            Files.createFile(targetPath.resolve("Wallet " + userEmail +"\\README.txt"));
            Files.writeString(targetPath.resolve("Wallet " + userEmail +"\\secretKey.txt"), key);
            Files.writeString(targetPath.resolve("Wallet " + userEmail +"\\README.txt"), warning);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
