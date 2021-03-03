package by.it.academy.miningservice;

import by.it.academy.miningservice.controller.ActiveThreadsContainer;
import by.it.academy.miningservice.main.BlockVerificationThread;
import by.it.academy.miningservice.repository.BlockRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MiningServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiningServiceApplication.class, args);

        // Starting of two independent utility threads.
        new ActiveThreadsContainer.ContainerGC().start();
        new BlockVerificationThread().start();
    }
}

