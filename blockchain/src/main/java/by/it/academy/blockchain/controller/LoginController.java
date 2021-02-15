package by.it.academy.blockchain.controller;

import by.it.academy.blockchain.configuration.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;

    @GetMapping("/login")
    public ModelAndView returnLoginPage (ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView returnLoginPageWithError (ModelAndView modelAndView) {
        daoAuthenticationProvider.
        modelAndView.addObject("error", "Wrong password or email");
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
