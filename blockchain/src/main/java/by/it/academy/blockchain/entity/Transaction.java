package by.it.academy.blockchain.entity;

import by.it.academy.blockchain.enums.TransactionStatus;
import lombok.*;

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

    @Id
    private String id;

    @Column (name = "sender_public_key", nullable = false)
    private String senderPublicKey;

    @Column (name = "receiver_public_key", nullable = false)
    private String receiverPublicKey;

    @Column (name = "signature", nullable = false)
    private String signature;

    @Column (name = "value", nullable = false, precision = 19, scale = 4)
    private BigDecimal value;

    @Column (name = "comission", nullable = false, precision = 19, scale = 4)
    private BigDecimal comission;

    @Column(name = "date_of_creation", nullable = false, columnDefinition="TIMESTAMP", updatable = false)
    private LocalDateTime date;

    @Column (name = "status", nullable = false)
    @Enumerated (EnumType.STRING)
    private TransactionStatus status;
}
