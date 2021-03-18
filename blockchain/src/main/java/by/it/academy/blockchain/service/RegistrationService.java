package by.it.academy.blockchain.service;

import by.it.academy.blockchain.RSA.FileWriterUtil;
import by.it.academy.blockchain.RSA.RSAGenUtil;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Path;
import java.security.KeyPair;

@Service
public class RegistrationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    FileWriterUtil fileWriterUtil;

    public ModelAndView checkForErrorsAndSaveIfOk(User user, String confirmed, ModelAndView modelAndView) {
        if (!user.getPassword().equals(confirmed)) {
            modelAndView.setViewName("registration");
            modelAndView.addObject(user);
            modelAndView.addObject("passwordError", "Passwords are different!");
            return modelAndView;
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            modelAndView.setViewName("registration");
            modelAndView.addObject(user);
            modelAndView.addObject("emailError", "User with this email is registered! Choose another email");
            return modelAndView;
        }
        KeyPair keyPair = RSAGenUtil.generateKeyPair(); // generating new KeyPair
        userService.saveRegisteredUser(user, keyPair);
        modelAndView.addObject("targetPath", writePKToFileAndReturnPath(user, keyPair).toString());
        modelAndView.addObject("newUser", user);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    private Path writePKToFileAndReturnPath (User user, KeyPair keyPair) {
        String privateKey = RSAGenUtil.stringPrivateKey(keyPair.getPrivate());
        return fileWriterUtil.writeKeyToFile(user.getUsername(), privateKey);
    }
}
