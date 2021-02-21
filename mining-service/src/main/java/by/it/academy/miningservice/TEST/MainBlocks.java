package by.it.academy.miningservice.TEST;

import by.it.academy.miningservice.dto.Block;
import org.springframework.web.client.RestTemplate;

public class MainBlocks {

    public static final String BlockURL = "http://localhost:8080/blockchain/blocks";

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        try {
            Block forObject = restTemplate.getForObject(BlockURL + "/getLast", Block.class);
        } catch (Exception e){
            System.out.println("Net blokov!");
        }


    }
}
