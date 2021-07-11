package ch.hesge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.hesge.metier.UserMetier;
import ch.hesge.metier.UserMetierImpl;

@SpringBootApplication
public class MicrolearningApplication {
	
	@Autowired
	private UserMetier userMetier;
	
	public static void main(String[] args) {
		SpringApplication.run(MicrolearningApplication.class, args);
		//System.out.println(userm);
	}

}
