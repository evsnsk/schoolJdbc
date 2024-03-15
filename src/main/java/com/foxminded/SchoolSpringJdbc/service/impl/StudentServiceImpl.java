package com.foxminded.SchoolSpringJdbc.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.SchoolSpringJdbc.model.Course;
import com.foxminded.SchoolSpringJdbc.model.Group;
import com.foxminded.SchoolSpringJdbc.model.Student;
import com.foxminded.SchoolSpringJdbc.repository.GroupRepository;
import com.foxminded.SchoolSpringJdbc.repository.StudentRepository;
import com.foxminded.SchoolSpringJdbc.service.StudentService;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	private final GroupRepository groupRepository;

	public StudentServiceImpl(StudentRepository studentRepository, GroupRepository groupRepository) {
		this.studentRepository = studentRepository;
		this.groupRepository = groupRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Student findById(int id) {
		log.info("Find Student by ID={}", id);
		Optional<Student> student = studentRepository.findById(id);
		if (student.isPresent()) {
			log.info("Student by ID={} is Present", id);
			return student.get();
		} else {
			log.warn("Student by ID={} doesn't exist", id);
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> findAll() {
		log.info("Get all Students");
		return studentRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> findAllByCourseName(String courseName) {
		log.info("Find all Students by Course_NAME={}", courseName);
		return studentRepository.findAllByCourseName(courseName);
	}

	@Override
	public Student save(Student student) {
		log.info("Save {}", student);
		Optional<Group> optionalGroup = groupRepository.findById(student.getGroup().getId());
		if (optionalGroup.isPresent()) {
			Group group = optionalGroup.get();

			student.setGroup(group);

			studentRepository.save(student);

			return student;
		} else {
			throw new EntityNotFoundException("Group with id " + student.getGroup().getId() + " not found");
		}
	}

	@Override
	public void deleteById(int id) {
		log.info("Delete Student by ID={}", id);
		try {
			Student studentToRemove = studentRepository.findById(id).get();
			List<Student> newStudents = studentToRemove.getGroup().getStudents();
			newStudents.remove(studentToRemove);

			Group group = groupRepository.findById(studentToRemove.getGroup().getId()).get();
			group.setStudents(newStudents);
			groupRepository.saveAndFlush(group);
			group = groupRepository.findById(studentToRemove.getGroup().getId()).get();
			log.info("NEW STUDENTS", group);
			studentRepository.findById(id).get().setGroup(null);
			studentRepository.deleteById(id);
			studentRepository.flush();

		} catch (NoSuchElementException e) {
			log.info("Delete Student by ID: No Such Id={}", id);
		}
	}

	@Override
	public void addStudentToCourse(Student student, Course course) {
		log.info("Add Student to Course", student, course);
		studentRepository.addStudentToCourse(student, course);
	}

	@Override
	public void removeStudentFromCourse(Student student, Course course) {
		log.info("Remove Student from Course", student, course);
		studentRepository.removeStudentFromCourse(student, course);

	}

}
