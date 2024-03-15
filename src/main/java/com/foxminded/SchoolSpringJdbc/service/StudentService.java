package com.foxminded.SchoolSpringJdbc.service;

import java.util.List;

import com.foxminded.SchoolSpringJdbc.model.Course;
import com.foxminded.SchoolSpringJdbc.model.Student;

public interface StudentService {

	Student findById(int id);

	List<Student> findAll();

	List<Student> findAllByCourseName(String courseName);

	Student save(Student student);

	void deleteById(int id);

	void addStudentToCourse(Student student, Course course);

	void removeStudentFromCourse(Student student, Course course);
}
