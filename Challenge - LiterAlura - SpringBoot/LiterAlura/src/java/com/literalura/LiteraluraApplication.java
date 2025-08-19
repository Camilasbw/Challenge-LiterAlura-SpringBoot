package com.literalura;

import com.literalura.service.PrincipalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
    private final PrincipalService principalService;

    public LiteraluraApplication(PrincipalService principalService) {
        this.principalService = principalService;
    }

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        principalService.exibirMenu();
    }
}