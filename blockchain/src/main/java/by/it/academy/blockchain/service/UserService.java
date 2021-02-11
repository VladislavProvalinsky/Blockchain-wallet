package by.it.academy.blockchain.service;

import by.it.academy.blockchain.domain.UserView;
import by.it.academy.blockchain.entity.Role;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.repository.RoleRepository;
import by.it.academy.blockchain.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    WalletService walletService;


    @Transactional
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException(String.format("User %s not found!", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthority(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthority (Set<Role> roles){
        return roles.stream()
                .map(el-> new SimpleGrantedAuthority(el.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    @JsonView(UserView.RequiredFieldView.class)
    public User getOne(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
