package com.ensiklopediaulos.ditenun.configs;

import com.ensiklopediaulos.ditenun.services.ulospedia.UlosService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedConfig implements CommandLineRunner {

    private final UlosService ulosService;

    public SeedConfig(UlosService ulosService) {
        this.ulosService = ulosService;
    }

    @Override
    public void run(String... args) {
        ulosService.seedColorData();
    }

}
