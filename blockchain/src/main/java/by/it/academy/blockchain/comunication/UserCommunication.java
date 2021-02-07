package by.it.academy.blockchain.comunication;

import by.it.academy.blockchain.domain.UserView;
import by.it.academy.blockchain.pojo.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserCommunication {

    public static final String UserURL = "http://localhost:8080/blockchain/users/";

    @Autowired
    RestTemplate restTemplate;

    public User getUser (Long id) {
        return restTemplate.getForObject(UserURL+id, User.class);
    }

}
