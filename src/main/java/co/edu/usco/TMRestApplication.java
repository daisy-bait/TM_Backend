package co.edu.usco;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TMRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TMRestApplication.class, args);
    }

}
