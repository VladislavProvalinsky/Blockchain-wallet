package by.it.academy.blockchain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView returnLoginPage (ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
