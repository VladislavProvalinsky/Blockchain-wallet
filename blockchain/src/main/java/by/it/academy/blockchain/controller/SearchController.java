package by.it.academy.blockchain.controller;

import by.it.academy.blockchain.aspect.SecureAuthorization;
import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.service.TransactionService;
import by.it.academy.blockchain.service.UserService;
import by.it.academy.blockchain.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users/{id}")
public class SearchController {

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/search")
    @SecureAuthorization
    public ModelAndView searchTxResult(@RequestParam("searchParam") String searchParam,
                                     @PathVariable("id") Long id,
                                     @PageableDefault(sort = {"date"},
                                             direction = Sort.Direction.DESC) Pageable pageable,
                                     ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Page<Transaction> page = transactionService.findByReceiverPublicKey(user.getWallet().getId(), pageable);
        page = transactionService.filterTxBySearch(page, searchParam);
        modelAndView.addObject("user", user);
        modelAndView.addObject("page", page);
        modelAndView.addObject("url", "/blockchain/users/"+id+"/transactions");
        modelAndView.setViewName("user/userTransactions");
        return modelAndView;
    }

    @GetMapping("/searchWallet")
    @SecureAuthorization
    public ModelAndView searchWalletResult(@RequestParam("searchParam") String searchParam,
                                     @PathVariable("id") Long id,
                                     @PageableDefault(sort = {"id"},
                                             direction = Sort.Direction.DESC) Pageable pageable,
                                     ModelAndView modelAndView) {
        User hoster = userService.getOne(id);
        Page<User> page = userService.getAllUsers(pageable);
        page = userService.filterWalletBySearch(page, searchParam);
        modelAndView.addObject("user", hoster);
        modelAndView.addObject("page", page);
        modelAndView.addObject("url", "/blockchain/users/"+id+"/wallets");
        modelAndView.setViewName("user/allWallets");
        return modelAndView;
    }
}
