package by.it.academy.blockchain.controller;

import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.pojo.TransactionMock;
import by.it.academy.blockchain.service.UserService;
import by.it.academy.blockchain.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @GetMapping("/users/{id}/search")
    public ModelAndView searchResult(@RequestParam("searchParam") String searchParam,
                                     @PathVariable("id") Long id,
                                     ModelAndView modelAndView) {
        User user = userService.getOne(id);
        Wallet wallet = walletService.getOneByUserId(id);
        List<Transaction> transactions = wallet.getTransactions();
        List<Transaction> filteredTransactions = transactions
                .stream()
                .filter(tx -> tx.getValue().toString().contains(searchParam) ||
                        tx.getComission().toString().contains(searchParam) ||
                        tx.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).contains(searchParam) ||
                        tx.getStatus().getName().contains(searchParam))
                .collect(Collectors.toList());
        List<TransactionMock> mockingTransactions = TransactionMock.getMockingTransactions(filteredTransactions);
        modelAndView.addObject("user", user);
        modelAndView.addObject("wallet", wallet);
        modelAndView.addObject("transactionList", mockingTransactions);
        modelAndView.setViewName("userTransactions");
        return modelAndView;
    }
}
