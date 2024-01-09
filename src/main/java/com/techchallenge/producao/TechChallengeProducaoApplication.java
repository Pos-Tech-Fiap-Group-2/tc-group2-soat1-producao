package com.techchallenge.producao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class TechChallengeProducaoApplication {
	public static void main(String[] args) {
		SpringApplication.run(TechChallengeProducaoApplication.class, args);
	}
}
