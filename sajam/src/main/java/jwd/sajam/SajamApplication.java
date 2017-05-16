package jwd.sajam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class SajamApplication 
	extends SpringBootServletInitializer {
	
	@Autowired 
	private TestData td;

	public static void main(String[] args) {
		 SpringApplication.run(SajamApplication.class, args);
	}

}
