package com.foxminded.SchoolSpringJdbc.service;

import java.util.List;

import com.foxminded.SchoolSpringJdbc.model.Course;

public interface CourseService {

	Course findById(int id);

	Course findByName(String name);

	List<Course> findAll();

	Course save(Course course);

	void deleteById(int id);

	List<Course> findAllByStudentId(int studentId);

}
