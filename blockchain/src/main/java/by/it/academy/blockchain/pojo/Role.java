package by.it.academy.blockchain.pojo;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Role{

    private Long id;
    private String name; // Имя роли должно соответствовать шаблону: «ROLE_ИМЯ»
    private Set<User> users = new HashSet<>();

}
