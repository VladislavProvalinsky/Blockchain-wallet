package by.it.academy.blockchain.repository;

import by.it.academy.blockchain.domain.UserView;
import by.it.academy.blockchain.entity.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername (String username);

}
