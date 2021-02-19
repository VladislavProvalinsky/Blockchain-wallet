package by.it.academy.blockchain.controller;

import by.it.academy.blockchain.comunication.UserCommunication;
import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.dto.TransactionMock;
import by.it.academy.blockchain.service.TransactionService;
import by.it.academy.blockchain.service.UserService;
import by.it.academy.blockchain.service.WalletService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Logger;

@RestController
@Log
@RequestMapping("/users/{id}")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserCommunication userCommunication;


    @GetMapping
    public ModelAndView homeUser(@PathVariable("id") String id, ModelAndView modelAndView) {
        User user = userService.getOne(Long.valueOf(id));
        Wallet wallet = walletService.getOneByUserId(Long.valueOf(id));
        Double actualBalance = walletService.getActualBalance(wallet);
//        by.it.academy.blockchain.pojo.User user = userCommunication.getUser(id);
        modelAndView.addObject("actualBalance", actualBalance);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("userHome");
        return modelAndView;
    }

    @GetMapping("/transactionForm")
    public ModelAndView getTransactionForm(@PathVariable("id") Long id, ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = walletService.getOneByUserId(id);
        Double actualBalance = walletService.getActualBalance(wallet);
        modelAndView.addObject("actualBalance", actualBalance);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("transactionForm");
        return modelAndView;
    }

    @PostMapping("/new_transaction")
    public ModelAndView postNewTransaction(@PathVariable("id") Long id,
                                           @ModelAttribute("privateKey") String privateKey,
                                           @ModelAttribute("transaction") Transaction transaction,
                                           ModelAndView modelAndView) {
        if (transaction.getComission() == null) transaction.setComission(BigDecimal.ZERO);
        transaction.setValue(transaction.getValue().setScale(4, RoundingMode.CEILING));
        transaction.setComission(transaction.getComission().setScale(4, RoundingMode.CEILING));
        Wallet wallet = walletService.getOneByUserId(id);
        if (walletService.getOne(transaction.getReceiverPublicKey()) == null) {
            log.info("Check 1 ------------------------");
            modelAndView.addObject("receiverPublicKeyError", "No such receiver wallet in blockchain.");
            modelAndView.setViewName("redirect:/users/" + id + "/transactionForm");
        } else if (walletService.getOne(transaction.getSenderPublicKey()) == null) {
            log.info("Check 2 ------------------------");
            modelAndView.addObject("senderPublicKeyError", "Your walletID, was incorrect.");
            modelAndView.setViewName("redirect:/users/" + id + "/transactionForm");
        } else if ((transaction.getValue().doubleValue() + transaction.getComission().doubleValue()) > walletService.getActualBalance(wallet)) {
            log.info("Check 3 ------------------------");
            modelAndView.addObject("valueError", "Value of transaction more than your balance.");
            modelAndView.setViewName("redirect:/users/" + id + "/transactionForm");
        } else {
            log.info("------------------------");
            transactionService.putNewTransaction(wallet, transaction, privateKey);
            modelAndView.setViewName("redirect:/users/" + id);
        }
        return modelAndView;
    }

    @GetMapping("/transactions")
    public ModelAndView getTransactionsList(@PathVariable("id") Long id, ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = walletService.getOneByUserId(id);
        List<Transaction> transactions = wallet.getTransactions();
        // :) эта фигня чисто для view, никаких подделок не беспокойтесь)))
        List<TransactionMock> mockingTransactions = TransactionMock.getMockingTransactions(transactions);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.addObject("transactionList", mockingTransactions);
        modelAndView.setViewName("userTransactions");
        return modelAndView;
    }

}
