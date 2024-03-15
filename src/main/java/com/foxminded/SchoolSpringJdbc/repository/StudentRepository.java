package com.foxminded.SchoolSpringJdbc.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxminded.SchoolSpringJdbc.model.Course;
import com.foxminded.SchoolSpringJdbc.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	@Query("SELECT s FROM Student s JOIN s.courses WHERE name = :courseName")
	List<Student> findAllByCourseName(@Param("courseName") String courseName);

	@Modifying
	default void addStudentToCourse(Student student, Course course) {
		List<Course> newCourses = new ArrayList<>();
		if (student.getCourses() != null) {
			newCourses.addAll(student.getCourses());
		}
		newCourses.add(course);
		student.setCourses(newCourses);

		save(student);
	}

	@Modifying
	default void removeStudentFromCourse(Student student, Course course) {
		if (student.getCourses().contains(course)) {
			List<Course> newCourses = new ArrayList<>();
			newCourses.addAll(student.getCourses());
			newCourses.remove(course);
			student.setCourses(newCourses);
			save(student);
		}
	}

}
