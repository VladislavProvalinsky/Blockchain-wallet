package by.it.academy.blockchain.repository;

import by.it.academy.blockchain.entity.Output;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutputRepository extends JpaRepository<Output, String> {


}
