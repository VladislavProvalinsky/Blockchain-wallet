package by.it.academy.blockchain.controller;

import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.service.RegistrationService;
import by.it.academy.blockchain.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Log
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @GetMapping
    public ModelAndView getRegistrationPage (ModelAndView modelAndView) {
        User user = User.builder()
                .name("Vlad")
                .surname("Provalinsky")
                .username("vpr@mail.ru")
                .mobile("+375292941812")
                .build();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/new_user")
    public ModelAndView addUser(@ModelAttribute("user") User user,
                                @ModelAttribute("passwordConfirm") String confirmed,
                                ModelAndView modelAndView) {
        log.info("-----------------------------");
        return registrationService.checkForErrorsAndSaveIfOk(user, confirmed, modelAndView);
    }
}
