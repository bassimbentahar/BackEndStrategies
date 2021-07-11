package ch.hesge.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import ch.hesge.dao.UsersRepository;
import ch.hesge.model.User;

@Service
public class UserMetierImpl implements UserMetier {
	
	@Autowired
	private UsersRepository userRepository;

	public List<User> findAll() {
			
		return userRepository.findAll();
	}
	

	

}
