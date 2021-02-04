package by.it.academy.blockchain.controller;

import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.repository.UserRepository;
import by.it.academy.blockchain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public ModelAndView getRegistrationPage (ModelAndView modelAndView) {
        User user = new User("Vlad", "Provalinsky", "100", "vpr@mail.ru", "+375292941812");
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration/new_user")
    public ModelAndView addUser(@ModelAttribute("user") User user,
                                @ModelAttribute("passwordConfirm") String confirmed,
                                ModelAndView modelAndView) {
        System.out.println("------------------");
        if (!user.getPassword().equals(confirmed)) {
            modelAndView.setViewName("registration");
            modelAndView.addObject(user);
            modelAndView.addObject("passwordError", "Пароли не совпадают");
            return modelAndView;
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            modelAndView.setViewName("registration");
            modelAndView.addObject(user);
            modelAndView.addObject("emailError", "Пользователь с таким email уже существует");
            return modelAndView;
        }
        userService.saveRegisteredUser(user);
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
