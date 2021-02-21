package by.it.academy.miningservice;

import by.it.academy.miningservice.comunication.TransactionCommunication;
import by.it.academy.miningservice.dto.Transaction;
import by.it.academy.miningservice.dto.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MiningServiceApplicationTests {

    public static final String TxURL = "http://localhost:8080/blockchain/transactions";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TransactionCommunication transactionCommunication;

    @Test
    public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedListObjects() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TxURL)
                .queryParam("status", TransactionStatus.NOT_CONFIRMED)
                .queryParam("comission", BigDecimal.ZERO.setScale(4, RoundingMode.CEILING).toString());

        HttpEntity<TransactionStatus> entity = new HttpEntity<>(TransactionStatus.NOT_CONFIRMED);

        Mockito.when(restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Transaction>>() {
                }))
                .thenReturn(new ResponseEntity<List<Transaction>>(HttpStatus.OK));
    }

}
