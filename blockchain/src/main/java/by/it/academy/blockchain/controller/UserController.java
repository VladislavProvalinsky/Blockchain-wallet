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

import javax.persistence.NoResultException;

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
    public ModelAndView homeUser(@PathVariable("id") String id, ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = walletService.getOne(id);
        Double actualBalance = walletService.getActualBalance(wallet);
//        by.it.academy.blockchain.pojo.User user = userCommunication.getUser(id);
        modelAndView.addObject("actualBalance", actualBalance);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @GetMapping("/users/{id}/transactionForm")
    public ModelAndView getTransactionForm(@PathVariable("id") String id, ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = walletService.getOne(id);
        Double actualBalance = walletService.getActualBalance(wallet);
        modelAndView.addObject("actualBalance", actualBalance);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("transactionForm");
        return modelAndView;
    }

    @PostMapping("/users/{id}/new_transaction")
    public ModelAndView postNewTransaction(@PathVariable("id") String id,
                                           @ModelAttribute("privateKey") String privateKey,
                                           @ModelAttribute("transaction") Transaction transaction,
                                           ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = walletService.getOne(id);
        if (walletService.getOne(transaction.getReceiverPublicKey()) == null) {
            System.out.println("Check 1 --------------------");
            modelAndView.addObject("receiverPublicKeyError", "No such receiver wallet in blockchain.");
            modelAndView.setViewName("redirect:/users/"+id+"/transactionForm");
        } else if (walletService.getOne(transaction.getSenderPublicKey()) == null) {
            System.out.println("Check 2 --------------------");
            modelAndView.addObject("senderPublicKeyError", "Your walletID, was incorrect.");
            modelAndView.setViewName("redirect:/users/"+id+"/transactionForm");
        } else if (wallet.getTransactions() == null && transaction.getValue() + transaction.getComission() > walletService.getActualBalance(wallet)) {
            System.out.println("Check 3 --------------------");
            modelAndView.addObject("valueError", "Value of transaction more than your balance.");
            modelAndView.setViewName("redirect:/users/"+id+"/transactionForm");
        } else {
            System.out.println("-------------------------");
            transactionService.putNewTransaction(wallet, transaction, privateKey);
            modelAndView.setViewName("redirect:/users/" + id);
        }
        return modelAndView;
    }

}
