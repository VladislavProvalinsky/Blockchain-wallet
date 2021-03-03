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
        modelAndView.addObject("actualBalance", actualBalance);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("user/userHome");
        return modelAndView;
    }

    @GetMapping("/transactionForm")
    public ModelAndView getTransactionForm(@PathVariable("id") Long id,
                                           @RequestParam(name = "receiverPublicKeyError", required = false) String receiverPublicKeyError,
                                           @RequestParam(name = "senderPublicKeyError", required = false) String senderPublicKeyError,
                                           @RequestParam(name = "valueError", required = false) String valueError,
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
        return transactionService.validateTransactionFormAndCreateIfGood(transaction, id, privateKey, modelAndView);
    }

    @GetMapping("/transactions")
    public ModelAndView getTransactionsList(@PathVariable("id") Long id, ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = walletService.getOneByUserId(id);
        List<Transaction> transactions = wallet.getTransactions();
        List<TransactionMock> mockingTransactions = TransactionMock.getMockingTransactions(transactions);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.addObject("transactionList", mockingTransactions);
        modelAndView.setViewName("user/userTransactions");
        return modelAndView;
    }

}
