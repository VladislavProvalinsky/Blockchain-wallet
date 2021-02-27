package by.it.academy.blockchain.entity;

import by.it.academy.blockchain.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
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

    @Column (name = "value", nullable = false, precision = 19, scale = 4)
    private BigDecimal value; // сколько хотим потратить (зависит от input и output предыдущих транзакций)

    @Column (name = "comission", nullable = false, precision = 19, scale = 4)
    private BigDecimal comission; // комиссия для майнера (влияет на скорость подтверждения транзакции)

    @Column(name = "date_of_creation", nullable = false, columnDefinition="TIMESTAMP")
    private LocalDateTime date; // дата создания транзакции

    @Column (name = "status", nullable = false)
    @Enumerated (EnumType.STRING)
    private TransactionStatus status; // статус транзакции (подтвержденная, т.е попала в блок / неподтвержденная, т.е в очереди на попадание в блок)

    //block_id
    //wallet_id
}
