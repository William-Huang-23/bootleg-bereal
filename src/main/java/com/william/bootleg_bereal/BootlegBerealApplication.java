package com.william.bootleg_bereal;

import com.william.bootleg_bereal.security.ApiKeyAuthentication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class BootlegBerealApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootlegBerealApplication.class, args);
	}

}
