package vn.fs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GetabecShopApplication {

	public static void main(String[] args) {

		System.out.println(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("admin123"));

		SpringApplication.run(GetabecShopApplication.class, args);
	}

}
