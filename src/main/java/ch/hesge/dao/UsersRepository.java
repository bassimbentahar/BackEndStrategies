package ch.hesge.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import ch.hesge.model.User;


public interface UsersRepository extends JpaRepository<User, String> {
}
