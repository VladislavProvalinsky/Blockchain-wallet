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
@RequestMapping("/stop/{walletId}")
public class StopController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    BlockService blockService;


    @GetMapping
    public ResponseEntity<String> generateStopProcess(@PathVariable String walletId) {
        stopProcess(walletId);
        return new ResponseEntity<>("Stopped", HttpStatus.OK);
    }

    private void stopProcess (String walletId){
        ActiveThreadsContainer.stopProcess(walletId);

    }
}
