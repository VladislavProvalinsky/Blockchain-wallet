package by.it.academy.blockchain.service;

import by.it.academy.blockchain.configuration.CustomUserDetails;
import by.it.academy.blockchain.entity.User;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Selecting user from loadUserByUsername(String username)...........");
        User userEntity = userService.findByUsername(username);
        if (userEntity==null){
            throw new UsernameNotFoundException(String.format("User %s not found!", username));
        }
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }

}
