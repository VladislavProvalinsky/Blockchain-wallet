package by.it.academy.blockchain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Block {

    private String id; // хеш блока (который майнится по алгоритму: hash всех конфирмд транзакций + previous hash блока = 0x5...хеш)
    private String previousHash; // хеш предыдущего блока (суммируется с данными)
    private LocalDateTime date; // дата создания блока
    private List<Transaction> transactions = new ArrayList<>();

}
