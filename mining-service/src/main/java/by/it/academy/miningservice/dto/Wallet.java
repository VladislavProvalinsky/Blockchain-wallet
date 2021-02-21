package by.it.academy.miningservice.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Wallet {

    private String id; // публичный ключ и ID кошелька
    private Double input;
    private Double output;
    private List<Transaction> transactions = new ArrayList<>(); // список транзакций в кошельке
}
