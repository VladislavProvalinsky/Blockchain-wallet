package by.it.academy.blockchain.controller;

import by.it.academy.blockchain.aspect.SecureAuthorization;
import by.it.academy.blockchain.entity.*;
import by.it.academy.blockchain.service.BlockService;
import by.it.academy.blockchain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping ("/users/{id}")
public class BlockController {

    @Autowired
    BlockService blockService;

    @Autowired
    UserService userService;

    @GetMapping("/blocks")
    @SecureAuthorization
    public ModelAndView getBlockTree (@PathVariable("id") Long id, ModelAndView modelAndView) {
        User user = userService.getOne(id);
        List<Block> blockTree = blockService.getAllBlocks();
        modelAndView.addObject("user", user);
        modelAndView.addObject("blockTree", blockTree);
        modelAndView.setViewName("blockTree");
        return modelAndView;
    }
}
