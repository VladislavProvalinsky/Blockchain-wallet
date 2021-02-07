package by.it.academy.blockchain.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class User {

    private Long id; // id клиента в системе
    private String name; // имя клиента
    private String surname; // фамилия клиента
    private String password; // пароль для входа в систему {bcrypt}
    private String username; // email
    private String mobile; // моб телефон
    private Set<Role> roles = new HashSet<>();
    private List<Wallet> wallets = new ArrayList<>(); // кошелек клиента

}
