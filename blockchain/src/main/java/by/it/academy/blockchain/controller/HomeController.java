package by.it.academy.blockchain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping ("/")
    public ModelAndView homePage(ModelAndView modelAndView) {
        modelAndView.setViewName("home");
        return modelAndView;
    }

}
