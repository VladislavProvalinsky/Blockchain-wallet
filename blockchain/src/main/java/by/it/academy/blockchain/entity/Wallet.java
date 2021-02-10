package by.it.academy.blockchain.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@NoArgsConstructor
public class Wallet implements Serializable {
    private static final long serialVersionUID = -3365706083289255873L;

    @Id
//    @GeneratedValue(generator = "system-uuid") // генератор UUID
//    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id; // публичный ключ и ID кошелька

    @Column (name = "input", scale = 2)
    private Double input;

    @Column (name = "output", scale = 2)
    private Double output;

    @ToString.Exclude
    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn (name = "wallet_id")
    private List<Transaction> transactions = new ArrayList<>(); // список транзакций в кошельке

    public Wallet(String id, Double input) {
        this.id = id;
        this.input = input;
    }
}
