package by.it.academy.blockchain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "blocks")
@Data
@NoArgsConstructor
public class Block implements Serializable {
    private static final long serialVersionUID = -4682847241419425655L;

    //FIXME
    @Id
    @GeneratedValue(generator = "system-uuid") // генератор UUID
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id; // хеш блока (который майнится по алгоритму: hash всех конфирмд транзакций + previous hash блока = 0x5...хеш)

    @Column (name = "previous_hash", nullable = false)
    private String previousHash; // хеш предыдущего блока (суммируется с данными)

    @Column(name = "date_of_creation", nullable = false)
    private Timestamp timestamp; // дата создания блока

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn (name = "block_id")
    private List<Transaction> transactions = new ArrayList<>();

}
