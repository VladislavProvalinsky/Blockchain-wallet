package by.it.academy.blockchain.service;

import by.it.academy.blockchain.entity.Block;
import by.it.academy.blockchain.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlockService {

    @Autowired
    BlockRepository blockRepository;

    @Transactional
    public List<Block> getAllBlocks() {
        return blockRepository.findAll();
    }
}
