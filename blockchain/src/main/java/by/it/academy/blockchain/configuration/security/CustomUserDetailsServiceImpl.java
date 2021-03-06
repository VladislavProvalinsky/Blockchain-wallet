package by.it.academy.blockchain.configuration.security;

import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public CustomUserImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Selecting user from loadUserByUsername(String username)...........");
        User user = userService.findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException(String.format("User %s not found!", username));
        }

        return CustomUserFactory.fromUserToCustomUser(user);
    }

}
