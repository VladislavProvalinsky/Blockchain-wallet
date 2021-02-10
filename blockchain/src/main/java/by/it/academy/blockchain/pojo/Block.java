package by.it.academy.blockchain.pojo;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Block {

    private String id; // хеш блока (который майнится по алгоритму: hash всех конфирмд транзакций + previous hash блока = 0x5...хеш)
    private String previousHash; // хеш предыдущего блока (суммируется с данными)
    private LocalDateTime date; // дата создания блока
    private List<Transaction> transactions = new ArrayList<>();

}
