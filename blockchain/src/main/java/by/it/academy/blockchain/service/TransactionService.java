package by.it.academy.blockchain.service;

import by.it.academy.blockchain.RSA.RSAGenUtil;
import by.it.academy.blockchain.entity.Output;
import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.entity.Wallet;
import by.it.academy.blockchain.enums.TransactionStatus;
import by.it.academy.blockchain.repository.TransactionRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
public class TransactionService {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    public void putNewTransaction(Wallet wallet, Transaction transaction, String privateKey) {
        // сетим время и статус
        transaction.setDate(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.NOT_CONFIRMED);
        // считаем хеш и сетим в id
        String hashTransaction = RSAGenUtil.hashTransaction(transaction);
        transaction.setId(hashTransaction);
        // подписываем трназакцию и сетим цифр подпись
        String signature = RSAGenUtil.getSignature(RSAGenUtil.getPrivateFromString(privateKey), hashTransaction);
        transaction.setSignature(signature == null ? "Invalid signature" : signature);
        // сетим в кошелек готовую транзакцию
        wallet.getTransactions().add(transaction);
        // обновляем аутпут кошелька
        wallet.getOutputs().add(new Output(transaction.getValue().add(transaction.getComission())));
        // апдейтим кошелек
        walletService.updateWallet(wallet);
    }

    public ModelAndView validateTransactionFormAndCreateIfGood (Transaction transaction, Long id, String privateKey, ModelAndView modelAndView) {
        if (transaction.getComission() == null) transaction.setComission(BigDecimal.ZERO);
        transaction.setValue(transaction.getValue().setScale(4, RoundingMode.CEILING));
        transaction.setComission(transaction.getComission().setScale(4, RoundingMode.CEILING));
        Wallet wallet = userService.getOne(id).getWallet();
        if (walletService.getOne(transaction.getReceiverPublicKey()) == null) {
            log.info("Check 1 ------------------------");
            modelAndView.addObject("receiverPublicKeyError", "No receiver with this walletId!");
            modelAndView.setViewName("redirect:/users/" + id + "/transactionForm");
            return modelAndView;
        } else if (walletService.getOne(transaction.getSenderPublicKey()) == null) {
            log.info("Check 2 ------------------------");
            modelAndView.addObject("senderPublicKeyError", "Incorrect sender walletId!");
            modelAndView.setViewName("redirect:/users/" + id + "/transactionForm");
            return modelAndView;
        } else if ((transaction.getValue().doubleValue() + transaction.getComission().doubleValue()) > walletService.getActualBalance(wallet)) {
            log.info("Check 3 ------------------------");
            modelAndView.addObject("valueError", "Transaction value bigger than your balance!");
            modelAndView.setViewName("redirect:/users/" + id + "/transactionForm");
            return modelAndView;
        } else {
            log.info("------------------------");
            putNewTransaction(wallet, transaction, privateKey);
            modelAndView.setViewName("redirect:/users/" + id);
            return modelAndView;
        }
    }

    public Page<Transaction> findByReceiverPublicKey(String senderPublicKey, Pageable pageable) {
        return transactionRepository.findBySenderPublicKey(senderPublicKey, pageable);
    }

    public Page<Transaction> filterTxBySearch(Page<Transaction> page, String searchParam) {
        List<Transaction> filteredTransactions = page
                .stream()
                .filter(tx -> tx.getValue().toString().contains(searchParam) ||
                        tx.getComission().toString().contains(searchParam) ||
                        tx.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).contains(searchParam) ||
                        tx.getStatus().getName().contains(searchParam))
                .collect(Collectors.toList());
        return new PageImpl<>(filteredTransactions);
    }
}
