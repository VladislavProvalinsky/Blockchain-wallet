package by.it.academy.blockchain.entity;

import by.it.academy.blockchain.domain.UserView;
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
    @JsonView(UserView.RequiredFieldView.class)
    private Long id; // id клиента в системе

    @Column (name = "name", nullable = false)
    @JsonView(UserView.RequiredFieldView.class)
    private String name; // имя клиента

    @Column (name = "surname", nullable = false)
    @JsonView(UserView.RequiredFieldView.class)
    private String surname; // фамилия клиента

    @Column (name = "password", nullable = false)
    @JsonView(UserView.RequiredFieldView.class)
    private String password; // пароль для входа в систему {bcrypt}

    @Column (name = "email", nullable = false)
    @JsonView(UserView.RequiredFieldView.class)
    private String username; // login для входа в систему

    @Column (name = "mobile")
    @JsonView(UserView.RequiredFieldView.class)
    private String mobile; // моб телефон

    @ToString.Exclude
    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonView(UserView.FullView.class)
    private Set<Role> roles = new HashSet<>();


    @ToString.Exclude
    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn (name = "user_id")
    @JsonView(UserView.FullView.class)
    private List<Wallet> wallets = new ArrayList<>(); // кошелек клиента

    public User(String name, String surname, String username, String mobile) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.mobile = mobile;
    }
}
