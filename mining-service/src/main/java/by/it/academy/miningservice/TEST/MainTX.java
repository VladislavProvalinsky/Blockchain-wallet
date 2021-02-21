package by.it.academy.miningservice.TEST;

import by.it.academy.miningservice.dto.Transaction;
import by.it.academy.miningservice.dto.TransactionStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class MainTX {

    public static final String TxURL = "http://localhost:8080/blockchain/transactions";

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TxURL)
                .queryParam("status", TransactionStatus.NOT_CONFIRMED);

        ResponseEntity<List<Transaction>> exchange = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Transaction>>(){});

        exchange.getBody().forEach(System.out::println);
    }
}
