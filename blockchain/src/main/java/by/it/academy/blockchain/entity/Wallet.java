package by.it.academy.blockchain.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wallets")
@Data
@NoArgsConstructor
public class Wallet implements Serializable {
    private static final long serialVersionUID = -3365706083289255873L;

    @Id
    @GeneratedValue(generator = "system-uuid") // генератор UUID
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id; // публичный ключ и ID кошелька

    @Column (name = "input", scale = 2)
    private Double input;

    @Column (name = "output", scale = 2)
    private Double output;

    @ToString.Exclude
    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn (name = "wallet_id")
    private List<Transaction> transactions = new ArrayList<>(); // список транзакций в кошельке
}
