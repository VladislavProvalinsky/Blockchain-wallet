package by.it.academy.blockchain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {

    @GetMapping("/notAuthorized")
    public ModelAndView showAuthPage (ModelAndView modelAndView){
        modelAndView.setViewName("notAuthorized");
        return modelAndView;
    }
}
