package com.techchallenge.producao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
@Import({ContentSecurityPolicySecurityConfiguration.class, WebConfig.class})
public class TechChallengeProducaoApplication {
	public static void main(String[] args) {
		SpringApplication.run(TechChallengeProducaoApplication.class, args);
	}
}
