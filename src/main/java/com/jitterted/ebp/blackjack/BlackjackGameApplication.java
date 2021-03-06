package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.domain.GameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlackjackGameApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlackjackGameApplication.class, args);
    }

    //I am game directly
    @Bean
    public GameService createGameService() {
        return new GameService();
    }
}
