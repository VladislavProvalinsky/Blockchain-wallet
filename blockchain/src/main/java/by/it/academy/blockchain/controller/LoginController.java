package by.it.academy.blockchain.controller;

import by.it.academy.blockchain.configuration.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping("/login")
    public ModelAndView returnLoginPage (ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login/error")
    public ModelAndView returnLoginPageWithError (ModelAndView modelAndView) {
        modelAndView.addObject("error", "Wrong password or email");
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
