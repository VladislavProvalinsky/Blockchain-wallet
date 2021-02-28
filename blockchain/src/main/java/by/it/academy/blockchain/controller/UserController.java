package by.it.academy.blockchain.controller;

import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.entity.TransactionMock;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.service.TransactionService;
import by.it.academy.blockchain.service.UserService;
import by.it.academy.blockchain.service.WalletService;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
@Log
@RequestMapping("/users/{id}")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionService transactionService;


    @GetMapping
    public ModelAndView homeUser(@PathVariable("id") Long id, ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = walletService.getOneByUserId(id);
        Double actualBalance = walletService.getActualBalance(wallet);
//        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("actualBalance", actualBalance);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("user/userHome");
        return modelAndView;
    }

    @GetMapping("/transactionForm")
    public ModelAndView getTransactionForm(@PathVariable("id") Long id,
                                           @RequestParam (value = "receiverPublicKeyError", required = false) String receiverPublicKeyError,
                                           @RequestParam (value = "senderPublicKeyError", required = false) String senderPublicKeyError,
                                           @RequestParam (value = "valueError", required = false) String valueError,
                                           ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = walletService.getOneByUserId(id);
        Double actualBalance = walletService.getActualBalance(wallet);
        modelAndView.addObject("actualBalance", actualBalance);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.addObject("receiverPublicKeyError", receiverPublicKeyError);
        modelAndView.addObject("senderPublicKeyError", senderPublicKeyError);
        modelAndView.addObject("valueError", valueError);
        modelAndView.setViewName("user/transactionForm");
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
            modelAndView.setViewName("redirect:/users/" + id + "/transactionForm");
        } else if (walletService.getOne(transaction.getSenderPublicKey()) == null) {
            log.info("Check 2 ------------------------");
            modelAndView.setViewName("redirect:/users/" + id + "/transactionForm");
        } else if ((transaction.getValue().doubleValue() + transaction.getComission().doubleValue()) > walletService.getActualBalance(wallet)) {
            log.info("Check 3 ------------------------");
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
        modelAndView.setViewName("user/userTransactions");
        return modelAndView;
    }

}
