package by.it.academy.blockchain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "blocks")
@Getter
@Setter
@NoArgsConstructor
public class Block implements Serializable {
    private static final long serialVersionUID = -4682847241419425655L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "hash")
    private String hash;

    @Column (name = "nonce")
    private String nonce;

    @Column (name = "previous_hash", nullable = false)
    private String previousHash;

    @Column(name = "date_of_creation", nullable = false, columnDefinition="TIMESTAMP", updatable = false)
    private LocalDateTime date;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn (name = "block_id")
    private List<Transaction> transactions = new ArrayList<>();

}
