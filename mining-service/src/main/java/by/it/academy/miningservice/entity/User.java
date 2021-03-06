package by.it.academy.miningservice.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table (name = "users")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -4951871627773349499L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id клиента в системе

    @Column (name = "active", nullable = false)
    private boolean isActive;

    @Column (name = "name", nullable = false)
    private String name; // имя клиента

    @Column (name = "surname", nullable = false)
    private String surname; // фамилия клиента

    @Column (name = "password", nullable = false)
    private String password; // пароль для входа в систему {bcrypt}

    @Column (name = "email", nullable = false)
    private String username; // login для входа в систему

    @Column (name = "mobile")
    private String mobile; // моб телефон

    @Column (name = "filename")
    private String filename;

    @ToString.Exclude
    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @ToString.Exclude
    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn (name = "user_id")
    private List<Wallet> wallets = new ArrayList<>(); // кошелек клиента

    public User(String name, String surname, String username, String mobile) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.mobile = mobile;
    }
}
