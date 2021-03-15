package by.it.academy.blockchain.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Log
@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView getLoginForm (ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/login_error")
    public ModelAndView getLoginFormWithError (ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        modelAndView.addObject("emailError", "Bad login or password!");
        return modelAndView;
    }
}
