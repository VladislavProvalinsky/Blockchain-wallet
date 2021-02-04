package by.it.academy.blockchain.service;

import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class RegistrationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

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
        userService.saveRegisteredUser(user);
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
