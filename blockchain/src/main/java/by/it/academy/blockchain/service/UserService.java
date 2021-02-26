package by.it.academy.blockchain.service;

import by.it.academy.blockchain.domain.UserView;
import by.it.academy.blockchain.entity.Role;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.repository.RoleRepository;
import by.it.academy.blockchain.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    WalletService walletService;


    public User findByUsername (String username){
       return userRepository.findByUsername(username);
    }

    @Transactional
    public void saveRegisteredUser(User user) {
        if (roleRepository.findById(1L).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.getRoles().add(new Role(1L, "ROLE_USER"));
            user.getWallets().add(walletService.registerNewWalletUtil(user));
            userRepository.save(user);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.getRoles().add(roleRepository.findById(1L).get());
            user.getWallets().add(walletService.registerNewWalletUtil(user));
            userRepository.save(user);
        }
    }

    @JsonView(UserView.RequiredFieldView.class)
    public User getOne(Long id) {
        return userRepository.findById(id).orElseThrow(); // убрать однозданчно!
    }
}
