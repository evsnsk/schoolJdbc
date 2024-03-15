package com.foxminded.SchoolSpringJdbc.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.foxminded.SchoolSpringJdbc.model.Course;
import com.foxminded.SchoolSpringJdbc.model.Group;
import com.foxminded.SchoolSpringJdbc.model.Student;
import com.foxminded.SchoolSpringJdbc.repository.GroupRepository;
import com.foxminded.SchoolSpringJdbc.repository.StudentRepository;
import com.foxminded.SchoolSpringJdbc.service.StudentService;

@ActiveProfiles("test")
@SpringBootTest(classes = { StudentServiceImpl.class })
class StudentServiceImplTest {

	@MockBean
	StudentRepository studentRepository;

	@MockBean
	GroupRepository groupRepository;

	@Autowired
	StudentService studentService;

	@Test
	void test_findById() {
		Group group = new Group(1, "Group1");
		Student expected = new Student(1, group, "Name1", "LastName1");

		Optional<Student> student = Optional.of(expected);
		when(studentRepository.findById(1)).thenReturn(student);

		Student actual = studentService.findById(1);

		verify(studentRepository).findById(1);
		assertEquals(expected, actual);

	}

	@Test
	void test_findById_NoSuchId() {
		Optional<Student> student = Optional.empty();
		when(studentRepository.findById(-1)).thenReturn(student);

		Student actual = studentService.findById(-1);

		verify(studentRepository).findById(-1);
		assertEquals(null, actual);

	}

	@Test
	void test_findAll() {
		Group group = new Group(1, "Group1");
		List<Student> expected = List.of(new Student(1, group, "Name1", "LastName1"),
				new Student(2, group, "Name2", "LastName2"));

		List<Student> studetList = List.of(new Student(1, group, "Name1", "LastName1"),
				new Student(2, group, "Name2", "LastName2"));
		when(studentRepository.findAll()).thenReturn(studetList);

		List<Student> actual = studentService.findAll();

		verify(studentRepository).findAll();
		assertEquals(expected, actual);
	}

	@Test
	void test_findAll_EmptyDB() {
		List<Student> expected = new ArrayList<>();
		when(studentRepository.findAll()).thenReturn(new ArrayList<>());

		List<Student> actual = studentService.findAll();

		verify(studentRepository).findAll();
		assertEquals(expected, actual);
	}

	@Test
	void test_save() {
		Group group = new Group(10001, "Group1");
		Student expected = new Student(1, group, "Name1", "LastName1");
		Student student = new Student();
		student.setId(1);
		student.setGroup(group);
		student.setFirstName("Name1");
		student.setLastName("LastName1");

		when(groupRepository.findById(10001)).thenReturn(Optional.of(group));
		when(studentRepository.save(student)).thenReturn(new Student(1, group, "Name1", "LastName1"));

		Student actual = studentService.save(student);

		verify(groupRepository).findById(10001);
		verify(studentRepository).save(student);
		assertEquals(expected, actual);
	}

	@Test
	void test_findAllByCourseName() {
		String courseName = "Math";
		Course course1 = new Course(1, "Math",
				"Mathematics is the science and study of quality, structure, space, and change.");
		Course course2 = new Course(2, "History",
				"History is the study of change over time, and it covers all aspects of human society.");
		List<Course> courseList = List.of(course1, course2);
		Group group = new Group(1, "Group1");
		List<Student> expected = List.of(new Student(1, group, "Name1", "LastName1", courseList));

		when(studentRepository.findAllByCourseName(courseName)).thenReturn(expected);

		List<Student> actual = studentService.findAllByCourseName(courseName);

		verify(studentRepository).findAllByCourseName(courseName);
		assertEquals(expected, actual);

	}

	@Test
	void test_findAllByCourseName_NoSuchName() {
		String courseName = "Math1";
		List<Student> expected = new ArrayList<>();

		when(studentRepository.findAllByCourseName(courseName)).thenReturn(expected);

		List<Student> actual = studentService.findAllByCourseName(courseName);

		verify(studentRepository).findAllByCourseName(courseName);
		assertTrue(actual.isEmpty());

	}

	@Test
	void test_deleteById() {
		Integer id = 10002;
		Group group = new Group(10002, "Group2");
		Student studentToRemove = new Student(10001, group, "Name1", "LastName1");

		when(studentRepository.findById(id)).thenReturn(Optional.of(studentToRemove));
		when(groupRepository.findById(id)).thenReturn(Optional.of(group));
		when(groupRepository.saveAndFlush(group)).thenReturn(group);

		studentService.deleteById(id);

		verify(studentRepository, times(2)).findById(id);
		verify(groupRepository, times(2)).findById(id);
		verify(studentRepository).deleteById(id);
		verify(studentRepository).flush();
	}

	@Test
	void test_removeStudentFromCourse() {
		Group group = new Group(1, "Group1");
		Course course1 = new Course(1, "Math",
				"Mathematics is the science and study of quality, structure, space, and change.");
		Course course2 = new Course(2, "History",
				"History is the study of change over time, and it covers all aspects of human society.");

		Student student = new Student(1, group, "Name1", "LastName1", List.of(course1, course2));

		studentService.removeStudentFromCourse(student, course2);

		verify(studentRepository).removeStudentFromCourse(student, course2);
	}

	@Test
	void test_addStudentToCourse() {
		Group group = new Group(1, "Group1");
		Student student = new Student(1, group, "Name1", "LastName1");
		Course course = new Course(1, "Math",
				"Mathematics is the science and study of quality, structure, space, and change.");

		studentService.addStudentToCourse(student, course);

		verify(studentRepository).addStudentToCourse(student, course);
	}

}
