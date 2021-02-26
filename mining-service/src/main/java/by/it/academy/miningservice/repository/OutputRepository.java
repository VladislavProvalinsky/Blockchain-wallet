package by.it.academy.miningservice.repository;

import by.it.academy.miningservice.entity.Output;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutputRepository extends JpaRepository<Output, String> {


}
