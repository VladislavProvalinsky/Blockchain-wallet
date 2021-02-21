package by.it.academy.miningservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public enum TransactionStatus {

    CONFIRMED ("CONFIRMED") ,
    NOT_CONFIRMED ("NOT CONFIRMED"),
    WRONG_HASH ("WRONG_HASH"),
    HASH_VERIFIED ("HASH_VERIFIED"),
    WRONG_SIGNATURE ("WRONG_SIGNATURE"),
    VERIFIED ("VERIFIED"),
    IN_BLOCK ("IN_BLOCK");

    private String name;
}
