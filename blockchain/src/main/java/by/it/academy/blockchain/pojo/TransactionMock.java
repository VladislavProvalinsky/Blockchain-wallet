package by.it.academy.blockchain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TransactionMock {

    private String id;
    private String senderPublicKey;
    private String receiverPublicKey;
    private String signature;
    private BigDecimal value;
    private BigDecimal comission;
    private String date;
    private String status;


    public static List<TransactionMock> getMockingTransactions(List<by.it.academy.blockchain.entity.Transaction> transactions) {
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
        return mockList;
    }

}
