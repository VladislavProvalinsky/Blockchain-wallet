package by.it.academy.miningservice.controller;

import by.it.academy.miningservice.main.BlockGenThread;
import by.it.academy.miningservice.main.TXVerificationThread;
import by.it.academy.miningservice.service.BlockService;
import by.it.academy.miningservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/start/{walletId}")
public class StartController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    BlockService blockService;


    @GetMapping
    public ResponseEntity<String> startProcess(@PathVariable String walletId) {
        createProcess(walletId);
        return new ResponseEntity<>("Working...", HttpStatus.OK);
    }

    private void createProcess (String walletId){
        TXVerificationThread txVerificationThread = new TXVerificationThread(transactionService, walletId);
        BlockGenThread blockGenThread = new BlockGenThread(blockService, transactionService, walletId);
        ActiveThreadsContainer.addElementsInProcess(txVerificationThread, blockGenThread);
        ActiveThreadsContainer.startProcess(txVerificationThread, blockGenThread);
    }


}
