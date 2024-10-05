package it.romolo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"it.romolo", "it.romolo.entity","it.romolo.repository","it.romolo.servizziRestFul"})
public class Restful2Application {

	public static void main(String[] args) {
		SpringApplication.run(Restful2Application.class, args);
	}

}
