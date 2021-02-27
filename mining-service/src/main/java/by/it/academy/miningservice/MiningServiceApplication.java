package by.it.academy.miningservice;

import by.it.academy.miningservice.controller.ActiveThreadsContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MiningServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiningServiceApplication.class, args);
        new ActiveThreadsContainer.ContainerGC().start();
    }
}

