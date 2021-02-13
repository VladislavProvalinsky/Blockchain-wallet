package by.it.academy.blockchain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@Controller
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class.getName());

    @GetMapping("/login")
    public ModelAndView returnLoginPage (ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView returnLoginPageWithError (ModelAndView modelAndView) {
        modelAndView.addObject("error", "Wrong password or email");
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
