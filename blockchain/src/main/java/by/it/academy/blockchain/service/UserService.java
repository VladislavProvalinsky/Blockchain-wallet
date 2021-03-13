package by.it.academy.blockchain.service;

import by.it.academy.blockchain.domain.UserView;
import by.it.academy.blockchain.entity.Role;
import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.repository.RoleRepository;
import by.it.academy.blockchain.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Value("${upload.path}")
    private String uploadPath;


    public User findByUsername (String username){
       return userRepository.findByUsername(username);
    }

    @Transactional
    public void saveRegisteredUser(User user) {
        if (roleRepository.findById(1L).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.getRoles().add(new Role(1L, "ROLE_USER"));
            user.setWallet(walletService.registerNewWalletUtil(user));
            user.setActive(false);
            userRepository.save(user);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.getRoles().add(roleRepository.findById(1L).get());
            user.setWallet(walletService.registerNewWalletUtil(user));
            user.setActive(false);
            userRepository.save(user);
        }
    }

    @JsonView(UserView.RequiredFieldView.class)
    public User getOne(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void uploadFile(Long id, MultipartFile file) throws IOException {
        User user = getOne(id);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFilename));
        user.setFilename(resultFilename);
        userRepository.save(user);
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> filterWalletBySearch(Page<User> page, String searchParam) {
        List<User> filteredUsers = page
                .stream()
                .filter(u -> u.getName().contains(searchParam) ||
                        u.getSurname().contains(searchParam) ||
                        u.getWallet().getId().contains(searchParam))
                .collect(Collectors.toList());
        return new PageImpl<>(filteredUsers);
    }
}
