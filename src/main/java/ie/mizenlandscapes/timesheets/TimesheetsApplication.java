package ie.mizenlandscapes.timesheets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication//(exclude = SecurityAutoConfiguration.class) //TODO remove this in production
public class TimesheetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimesheetsApplication.class, args);
	}

}
