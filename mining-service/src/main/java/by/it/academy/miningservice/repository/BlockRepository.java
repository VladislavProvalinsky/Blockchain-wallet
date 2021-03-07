package by.it.academy.miningservice.repository;


import by.it.academy.miningservice.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

}
