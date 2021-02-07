package by.it.academy.blockchain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role implements Serializable {
    private static final long serialVersionUID = -6491451139057526071L;

    @Id
    private Long id;

    @Column (name = "name")
    private String name; // Имя роли должно соответствовать шаблону: «ROLE_ИМЯ»

    @ToString.Exclude
    @Transient
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();


    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
