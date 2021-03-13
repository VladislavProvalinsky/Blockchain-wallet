package by.it.academy.blockchain.controller;

import by.it.academy.blockchain.aspect.SecureAuthorization;
import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.service.TransactionService;
import by.it.academy.blockchain.service.UserService;
import by.it.academy.blockchain.service.WalletService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

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
    @SecureAuthorization
    public ModelAndView homeUser(@PathVariable("id") Long id, ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = user.getWallet();
        Double actualBalance = walletService.getActualBalance(wallet);
        modelAndView.addObject("actualBalance", actualBalance);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("user/userHome");
        return modelAndView;
    }

    @GetMapping("/transactionForm")
    @SecureAuthorization
    public ModelAndView getTransactionForm(@PathVariable("id") Long id,
                                           @RequestParam(name = "receiverPublicKeyError", required = false) String receiverPublicKeyError,
                                           @RequestParam(name = "senderPublicKeyError", required = false) String senderPublicKeyError,
                                           @RequestParam(name = "valueError", required = false) String valueError,
                                           ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = user.getWallet();
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
    @SecureAuthorization
    public ModelAndView postNewTransaction(@PathVariable("id") Long id,
                                           @ModelAttribute("privateKey") String privateKey,
                                           @ModelAttribute("transaction") Transaction transaction,
                                           ModelAndView modelAndView) {
        return transactionService.validateTransactionFormAndCreateIfGood(transaction, id, privateKey, modelAndView);
    }

    @GetMapping("/transactions")
    @SecureAuthorization
    public ModelAndView getTransactionsList(@PathVariable("id") Long id,
                                            @PageableDefault (sort = {"date"},
                                                    direction = Sort.Direction.DESC) Pageable pageable,
                                            ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Page<Transaction> page = transactionService.findByReceiverPublicKey(user.getWallet().getId(), pageable);
        modelAndView.addObject("user", user);
        modelAndView.addObject("page", page);
        modelAndView.addObject("url", "/blockchain/users/"+id+"/transactions");
        modelAndView.setViewName("user/userTransactions");
        return modelAndView;
    }

    @GetMapping("/wallets")
    @SecureAuthorization
    public ModelAndView getAllWallets(@PathVariable("id") Long id,
                                            @PageableDefault (sort = {"id"},
                                                    direction = Sort.Direction.DESC) Pageable pageable,
                                            ModelAndView modelAndView) {
        User hoster = userService.getOne(id);
        Page<User> users = userService.getAllUsers(pageable);
        modelAndView.addObject("user", hoster);
        modelAndView.addObject("page", users);
        modelAndView.addObject("url", "/blockchain/users/"+id+"/wallets");
        modelAndView.setViewName("user/allWallets");
        return modelAndView;
    }

    @PostMapping("/upload")
    public ModelAndView uploadImage(@PathVariable("id") Long id,
                                    @RequestParam("file") MultipartFile file,
                                    ModelAndView modelAndView) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            userService.uploadFile(id, file);
        }
        modelAndView.setViewName("redirect:/users/" + id);
        return modelAndView;
    }
}
