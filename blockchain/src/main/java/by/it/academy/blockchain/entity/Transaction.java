package by.it.academy.blockchain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction implements Serializable {
    private static final long serialVersionUID = 5040651852162921242L;

    @Id
    @GeneratedValue(generator = "system-uuid") // генератор UUID
    @GenericGenerator(name = "system-uuid", strategy = "uuid2") // hash транзакции который высчитывается из всех данных по транзакции по sha-256
    private String id;

    @Column (name = "sender_public_key", nullable = false)
    private String senderPublicKey;

    @Column (name = "receiver_public_key", nullable = false)
    private String receiverPublicKey;

    @Column (name = "value", nullable = false, scale = 2)
    private Double value; // сколько хотим потратить (зависит от input и output предыдущих транзакций)

    @Column (name = "comission", nullable = false, scale = 2)
    private Double comission; // комиссия для майнера (влияет на скорость подтверждения транзакции)

    @Column (name = "status", nullable = false)
    private String status; // статус транзакции (подтвержденная, т.е попала в блок / неподтвержденная, т.е в очереди на попадание в блок)

    //block_id
    //wallet_id
}
