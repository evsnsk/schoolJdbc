package com.foxminded.SchoolSpringJdbc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.SchoolSpringJdbc.model.Course;
import com.foxminded.SchoolSpringJdbc.repository.CourseRepository;
import com.foxminded.SchoolSpringJdbc.service.CourseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;

	public CourseServiceImpl(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Course findById(int id) {
		log.info("Find Course by ID={}", id);
		Optional<Course> cource = courseRepository.findById(id);
		if (cource.isPresent()) {
			log.info("Course by ID={} is Present", id);
			return cource.get();
		} else {
			log.warn("Course by ID={} doesn't exist", id);
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Course findByName(String name) {
		log.info("Find Course by NAME={}", name);
		Optional<Course> cource = courseRepository.findByName(name);
		if (cource.isPresent()) {
			log.info("Course by NAME={} is Present", name);
			return cource.get();
		} else {
			log.warn("Course by NAME={} doesn't exist", name);
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Course> findAll() {
		log.info("Get all Courses");
		return courseRepository.findAll();
	}

	@Override
	public Course save(Course course) {
		log.info("Save {}", course);
		return courseRepository.save(course);
	}

	@Override
	public void deleteById(int id) {
		log.info("Delete Course by ID={}", id);
		courseRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Course> findAllByStudentId(int studentId) {
		log.info("Find all Courses by Student_ID={}", studentId);
		return courseRepository.findAllByStudentId(studentId);
	}

}
