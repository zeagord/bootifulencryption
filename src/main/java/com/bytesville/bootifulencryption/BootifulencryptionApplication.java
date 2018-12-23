package com.bytesville.bootifulencryption;

import com.github.javafaker.Faker;
import java.util.UUID;
import org.jooq.DSLContext;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.Plaintext;

import static org.jooq.example.db.h2.Tables.CUSTOMER;

@SpringBootApplication
public class BootifulencryptionApplication{

	public static void main(String[] args) {
		SpringApplication.run(BootifulencryptionApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(DSLContext dsl ) {
		VaultOperations vaultOperations = BeanUtil.getBean(VaultOperations.class);
		return args -> {
			for (int i = 0; i < 100; i++) {
				Faker faker = new Faker();
				dsl.insertInto(CUSTOMER, CUSTOMER.ID, CUSTOMER.FIRST_NAME, CUSTOMER.LAST_NAME,
						CUSTOMER.EMAIL_ADDRESS,
						CUSTOMER.CREDIT_CARD_NUMBER)
						.values(UUID.randomUUID().toString(),
								faker.name().firstName(),
								faker.name().lastName(),
								faker.internet().emailAddress(),
								vaultOperations.opsForTransit().encrypt("customer", Plaintext.of(faker.finance().creditCard()))
										.getCiphertext())
						.execute();
			}
		};
	}
}
