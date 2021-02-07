package by.it.academy.blockchain.pojo;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Wallet {

    private String id; // публичный ключ и ID кошелька
    private Double input;
    private Double output;
    private List<Transaction> transactions = new ArrayList<>(); // список транзакций в кошельке
}
