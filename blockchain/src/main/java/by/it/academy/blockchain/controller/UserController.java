package by.it.academy.blockchain.controller;

import by.it.academy.blockchain.comunication.UserCommunication;
import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.handleException.NoSuchWalletException;
import by.it.academy.blockchain.service.TransactionService;
import by.it.academy.blockchain.service.UserService;
import by.it.academy.blockchain.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserCommunication userCommunication;


    @GetMapping("/users/{id}")
    public ModelAndView homeUser(@PathVariable("id") Long id, ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = walletService.getOne(id);
//        by.it.academy.blockchain.pojo.User user = userCommunication.getUser(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @GetMapping("/users/{id}/transactionForm")
    public ModelAndView getTransactionForm(@PathVariable("id") Long id, ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = walletService.getOne(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("transactionForm");
        return modelAndView;
    }

    @PostMapping("/users/{id}/new_transaction")
    public ModelAndView postNewTransaction(@PathVariable("id") Long id,
                                           @ModelAttribute("privateKey") String privateKey,
                                           @ModelAttribute("transaction") Transaction transaction,
                                           ModelAndView modelAndView) {
        Wallet wallet = walletService.getOne(id);
        if (walletService.getOne(transaction.getReceiverPublicKey()) == null) {
            modelAndView.addObject("receiverPublicKeyError", "No such receiver wallet in blockchain.");
            modelAndView.setViewName("redirect:/users/" + id + "/transactionForm");
        }
        if (walletService.getOne(transaction.getSenderPublicKey()) == null) {
            modelAndView.addObject("senderPublicKeyError", "Your walletID, was incorrect.");
            modelAndView.setViewName("redirect:/users/" + id + "/transactionForm");
        }
        if (wallet.getTransactions() == null && transaction.getValue() + transaction.getComission() > wallet.getInput()) {
            modelAndView.addObject("valueError", "Value of transaction more than your balance.");
            modelAndView.setViewName("redirect:/users/" + id + "/transactionForm");
        } else {
            transactionService.putNewTransaction(wallet, transaction, privateKey);
            modelAndView.setViewName("user");
        }
        return modelAndView;
    }

}
