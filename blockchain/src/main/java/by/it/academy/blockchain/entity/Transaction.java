package by.it.academy.blockchain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Transaction implements Serializable {
    private static final long serialVersionUID = 5040651852162921242L;

    @Id // hash транзакции который высчитывается из всех данных по транзакции по sha-256
    private String id;

    @Column (name = "sender_public_key", nullable = false)
    private String senderPublicKey;

    @Column (name = "receiver_public_key", nullable = false)
    private String receiverPublicKey;

    @Column (name = "signature", nullable = false)
    private String signature; // хеш транзакции ((id) + private Key) подписанной цифровой подписью

    @Column (name = "value", nullable = false, scale = 2)
    private Double value; // сколько хотим потратить (зависит от input и output предыдущих транзакций)

    @Column (name = "comission", nullable = false, scale = 2)
    private Double comission; // комиссия для майнера (влияет на скорость подтверждения транзакции)

    @Column(name = "date_of_creation", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date; // дата создания транзакции

    @Column (name = "status", nullable = false)
    private String status; // статус транзакции (подтвержденная, т.е попала в блок / неподтвержденная, т.е в очереди на попадание в блок)

    //block_id
    //wallet_id
}
