package by.it.academy.blockchain.repository;

import by.it.academy.blockchain.entity.Input;
import by.it.academy.blockchain.entity.Output;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputRepository extends JpaRepository<Input, String> {


}
