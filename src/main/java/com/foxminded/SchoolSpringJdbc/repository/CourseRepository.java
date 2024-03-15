package com.foxminded.SchoolSpringJdbc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxminded.SchoolSpringJdbc.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

	Optional<Course> findByName(String name);

	@Query("SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId")
	List<Course> findAllByStudentId(@Param("studentId") int studentId);
}
