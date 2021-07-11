package ch.hesge.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.hesge.model.UserCourse;

public interface UserCourseRepository extends JpaRepository<UserCourse, String>{
    public Optional<UserCourse> findById(String id);

}
