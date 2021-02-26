package by.it.academy.miningservice.repository;


import by.it.academy.miningservice.entity.Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputRepository extends JpaRepository<Input, String> {


}
