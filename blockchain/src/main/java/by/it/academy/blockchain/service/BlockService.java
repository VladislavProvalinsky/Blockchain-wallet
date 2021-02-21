package by.it.academy.blockchain.service;

import by.it.academy.blockchain.entity.Block;
import by.it.academy.blockchain.repository.BlockRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Service
@Log
public class BlockService {

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    EntityManager entityManager;

    public Block getLastBlockFromDB() throws NoResultException {
        TypedQuery<Block> query = entityManager.createQuery("FROM Block B ORDER BY B.date DESC", Block.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
