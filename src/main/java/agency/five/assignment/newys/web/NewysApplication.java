package agency.five.assignment.newys.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@ComponentScan(basePackages = { "agency.five.assignment.newys" })
public class NewysApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewysApplication.class, args);
	}

}
