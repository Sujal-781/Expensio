package in.sujal.expensio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ExpensioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpensioApplication.class, args);
	}

}
