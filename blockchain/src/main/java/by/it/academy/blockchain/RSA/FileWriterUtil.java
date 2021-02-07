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

    public void writeKeyToFile(String unique, String key) {
        Path targetPath = Paths.get("C:\\Users\\Vladislav\\Desktop\\");
        try {
            Files.createDirectory(targetPath.resolve("Wallet " + unique));
            Files.createFile(targetPath.resolve("Wallet " + unique +"\\secretKey.txt"));
            Files.createFile(targetPath.resolve("Wallet " + unique +"\\README.txt"));
            Files.writeString(targetPath.resolve("Wallet " + unique +"\\secretKey.txt"), key);
            Files.writeString(targetPath.resolve("Wallet " + unique +"\\README.txt"), warning);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
