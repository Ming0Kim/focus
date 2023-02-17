package com.bb.focus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FocusApplication{

	public static void main(String[] args) {
		SpringApplication.run(FocusApplication.class, args);
	}

}
