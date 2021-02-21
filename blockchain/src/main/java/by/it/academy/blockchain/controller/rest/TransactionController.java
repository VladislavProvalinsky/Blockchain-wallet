package by.it.academy.blockchain.controller.rest;

import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.enums.TransactionStatus;
import by.it.academy.blockchain.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public List<Transaction> getFirst5NotConfirmedTransactions(@RequestParam TransactionStatus status) {
        return transactionService.getFirst5NotConfirmedTransactions(status);
    }

    @PutMapping("/putVerified")
    public void putCheckedTransactionsInDB(@RequestBody List<Transaction> transactions) {
        transactionService.putCheckedTransactionsInDB(transactions);
    }

    @GetMapping ("/getVerified")
    public List<Transaction> findFirst5ByStatusAndComissionGreaterThan (@RequestParam TransactionStatus status,
                                                                         @RequestParam BigDecimal comission) {
        return transactionService.findFirst5ByStatusAndComissionGreaterThan(status, comission);
    }

}
