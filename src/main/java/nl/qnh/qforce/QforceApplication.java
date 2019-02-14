package nl.qnh.qforce;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class QforceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QforceApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTeamplate() {
		return new RestTemplate();
	}

	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

}

