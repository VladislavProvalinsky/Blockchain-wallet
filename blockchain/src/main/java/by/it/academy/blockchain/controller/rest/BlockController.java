package by.it.academy.blockchain.controller.rest;

import by.it.academy.blockchain.entity.Block;
import by.it.academy.blockchain.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.NoResultException;

@Controller
@RequestMapping ("/blocks")
public class BlockController {

    @Autowired
    BlockService blockService;

    @GetMapping ("/getLast")
    public Block getLastBlockFromDB () throws NoResultException {
        return blockService.getLastBlockFromDB();
    }
}
