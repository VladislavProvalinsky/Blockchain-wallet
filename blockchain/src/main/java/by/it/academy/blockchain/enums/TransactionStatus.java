package by.it.academy.blockchain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public enum TransactionStatus {

    CONFIRMED ("CONFIRMED") ,
    NOT_CONFIRMED ("NOT CONFIRMED");

    private String name;
}
