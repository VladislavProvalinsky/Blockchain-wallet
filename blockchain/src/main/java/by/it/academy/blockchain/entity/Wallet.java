package by.it.academy.blockchain.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    private String id;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "wallet")
    private User user;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ToString.Exclude
    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn (name = "wallet_id")
    private List<Input> inputs = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ToString.Exclude
    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn (name = "wallet_id")
    private List<Output> outputs = new ArrayList<>();

    @ToString.Exclude
    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn (name = "wallet_id")
    private List<Transaction> transactions = new ArrayList<>();

    public Wallet(String id) {
        this.id = id;
    }
}
