package by.it.academy.miningservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role{

    private Long id;
    private String name; // Имя роли должно соответствовать шаблону: «ROLE_ИМЯ»
    private Set<User> users = new HashSet<>();

}
