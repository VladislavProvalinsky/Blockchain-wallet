package by.it.academy.miningservice;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class MiningServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiningServiceApplication.class, args);
    }
}

