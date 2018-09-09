package com.packa.japp.config;

import com.packa.japp.naivechain.BlockChainRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public BlockChainRepository blockChainRepository() {
        return new BlockChainRepository();
    }
}
