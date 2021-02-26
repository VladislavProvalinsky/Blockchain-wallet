package by.it.academy.miningservice.entity;

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
    private String hash; // хеш блока (который майнится по алгоритму: hash всех конфирмд транзакций + previous hash блока = 0x5...хеш)

    @Column (name = "nonce")
    private String nonce;

    @Column (name = "previous_hash", nullable = false)
    private String previousHash; // хеш предыдущего блока (суммируется с данными)

    @Column(name = "date_of_creation", nullable = false)
    private LocalDateTime date; // дата создания блока

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn (name = "block_id")
    private List<Transaction> transactions = new ArrayList<>();

}
