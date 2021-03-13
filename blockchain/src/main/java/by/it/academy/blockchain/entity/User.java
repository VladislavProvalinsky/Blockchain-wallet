package by.it.academy.blockchain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -4951871627773349499L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "active", nullable = false)
    private boolean isActive;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String username;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "filename")
    private String filename;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @ToString.Exclude
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "wallet_id")
    private Wallet wallet;

    public User(String name, String surname, String username, String mobile) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.mobile = mobile;
    }
}
