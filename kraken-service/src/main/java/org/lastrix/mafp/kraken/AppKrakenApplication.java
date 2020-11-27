package org.lastrix.mafp.kraken;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EntityScan
public class AppKrakenApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppKrakenApplication.class, args);
    }

    @Override
    public void run(String... args) {

    }

}
