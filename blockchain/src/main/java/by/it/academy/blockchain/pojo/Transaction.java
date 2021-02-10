package by.it.academy.blockchain.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transaction {

    private String id;
    private String senderPublicKey;
    private String receiverPublicKey;
    private String signature;
    private Double value; // сколько хотим потратить (зависит от input и output предыдущих транзакций)
    private Double comission; // комиссия для майнера (влияет на скорость подтверждения транзакции)
    private LocalDateTime date; // дата создания транзакции
    private String status; // статус транзакции (подтвержденная, т.е попала в блок / неподтвержденная, т.е в очереди на попадание в блок)
}
