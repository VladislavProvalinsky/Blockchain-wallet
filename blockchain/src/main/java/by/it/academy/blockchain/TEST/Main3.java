package by.it.academy.blockchain.TEST;

import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.entity.TransactionMock;
import by.it.academy.blockchain.enums.TransactionStatus;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main3 {

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>() {{
            add(Transaction.builder()
                    .id("1")
                    .comission(BigDecimal.valueOf(12.484115).setScale(2, RoundingMode.CEILING))
                    .date(LocalDateTime.now())
                    .receiverPublicKey("12345")
                    .senderPublicKey("54321")
                    .signature("00000")
                    .value(BigDecimal.TEN)
                    .status(TransactionStatus.NOT_CONFIRMED)
                    .build());
            add(Transaction.builder()
                    .id("2")
                    .comission(BigDecimal.valueOf(5.487).setScale(2, RoundingMode.CEILING))
                    .date(LocalDateTime.now())
                    .receiverPublicKey("5451515")
                    .senderPublicKey("kadjkl")
                    .signature("adsvksjf40")
                    .value(BigDecimal.valueOf(151.251).setScale(2, RoundingMode.CEILING))
                    .status(TransactionStatus.NOT_CONFIRMED)
                    .build());
        }};

        List<TransactionMock> mockList = new ArrayList<>();
        transactions.stream()
                .peek(e -> mockList.add(new TransactionMock(
                        e.getId(),
                        e.getSenderPublicKey(),
                        e.getReceiverPublicKey(),
                        e.getSignature(),
                        e.getValue(),
                        e.getComission(),
                        e.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        e.getStatus().getName())))
                .collect(Collectors.toList());


        mockList.forEach(System.out::println);
    }
}
