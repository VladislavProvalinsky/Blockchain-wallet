package by.it.academy.blockchain.controller;

import by.it.academy.blockchain.comunication.UserCommunication;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.service.UserService;
import by.it.academy.blockchain.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @Autowired
    UserCommunication userCommunication;


    @GetMapping("/users/{id}")
    public ModelAndView homeUser(@PathVariable ("id") Long id, ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = walletService.getOne(id);
//        by.it.academy.blockchain.pojo.User user = userCommunication.getUser(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @GetMapping("/users/{id}/transactionForm")
    public ModelAndView newTransaction (@PathVariable ("id") Long id, ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = walletService.getOne(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("transactionForm");
        return modelAndView;
    }

}
