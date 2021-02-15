package by.it.academy.blockchain.dto;

import by.it.academy.blockchain.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

    private String id;
    private String senderPublicKey;
    private String receiverPublicKey;
    private String signature;
    private BigDecimal value; // сколько хотим потратить (зависит от input и output предыдущих транзакций)
    private BigDecimal comission; // комиссия для майнера (влияет на скорость подтверждения транзакции)
    private LocalDateTime date; // дата создания транзакции
    private TransactionStatus status; // статус транзакции (подтвержденная, т.е попала в блок / неподтвержденная, т.е в очереди на попадание в блок)
}
