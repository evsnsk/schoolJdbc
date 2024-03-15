package com.foxminded.SchoolSpringJdbc.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.foxminded.SchoolSpringJdbc.model.Course;
import com.foxminded.SchoolSpringJdbc.model.Group;
import com.foxminded.SchoolSpringJdbc.model.Student;

@ActiveProfiles("test")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		StudentRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/clear_tables.sql",
		"/sql/sample_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StudentRepositoryTest {

	@Autowired
	StudentRepository studentRepository;

	@Test
	void test_findById() {
		Student expected = new Student(10001, "Name1", "LastName1");

		Optional<Student> actual = studentRepository.findById(10001);

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}

	@Test
	void test_findById_NoSuchId() {
		Optional<Student> actual = studentRepository.findById(-1);

		assertTrue(actual.isEmpty());
	}

	@Test
	void test_findAll() {
		List<Student> expected = List.of(new Student(10001, "Name1", "LastName1"),
				new Student(10002, "Name2", "LastName2"), new Student(10003, "Name3", "LastName3"),
				new Student(10004, "Name4", "LastName4"), new Student(10005, "Name5", "LastName5"),
				new Student(10006, "Name5", "LastName5"));

		List<Student> actual = studentRepository.findAll();

		assertTrue(!actual.isEmpty());
		assertEquals(expected, actual);
	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void test_findAll_NoData() {
		List<Student> actual = studentRepository.findAll();

		assertTrue(actual.isEmpty());
	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void test_save_EmptyId() {

		Group group = new Group("KN-21");
		Student student = new Student();
		student.setGroup(group);
		student.setFirstName("NameNEW");
		student.setLastName("LastNameNEW");
		student.setCourses(List.of(new Course("Name", "desc")));

		student = studentRepository.saveAndFlush(student);

		List<Student> expectedStudentList = List.of(student);

		List<Student> actualStudentList = studentRepository.findAll();
		Optional<Student> actualStudent = studentRepository.findById(student.getId());

		assertTrue(actualStudent.isPresent());
		assertEquals(expectedStudentList, actualStudentList);

	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void test_save_NoSuchId() {
		Student student = new Student("NameNEW", "LastNameNEW");

		student = studentRepository.save(student);

		List<Student> expectedStudentList = List.of(student);

		List<Student> actualStudentList = studentRepository.findAll();
		Optional<Student> actualStudent = studentRepository.findById(student.getId());

		assertTrue(actualStudent.isPresent());
		assertEquals(expectedStudentList, actualStudentList);
	}

	@Test
	void test_save_ById() {
		Student student = new Student("NameNEW", "LastNameNEW");

		studentRepository.save(student);
		student.setFirstName("NameNEW2");
		Student expectedStudent = student;

		List<Student> expectedStudentList = List.of(new Student(10001, "Name1", "LastName1"),
				new Student(10002, "Name2", "LastName2"), new Student(10003, "Name3", "LastName3"),
				new Student(10004, "Name4", "LastName4"), new Student(10005, "Name5", "LastName5"),
				new Student(10006, "Name6", "LastName6"), expectedStudent);

		List<Student> actualStudentList = studentRepository.findAll();
		Optional<Student> actualStudent = studentRepository.findById(10006);

		assertTrue(actualStudent.isPresent());
		assertEquals(expectedStudentList, actualStudentList);
	}

	@Test
	void test_delete() {

		studentRepository.findById(10003);
		Optional<Student> actualStudent = studentRepository.findById(10003);
		assertTrue(!actualStudent.isEmpty());

		studentRepository.deleteById(10003);

		actualStudent = studentRepository.findById(10003);

		assertTrue(actualStudent.isEmpty());
	}

	@Test
	void test_delete_NoSuchId() {
		List<Student> expectedStudentList = List.of(new Student(10001, "Name1", "LastName1"),
				new Student(10002, "Name2", "LastName2"), new Student(10003, "Name3", "LastName3"),
				new Student(10004, "Name4", "LastName4"), new Student(10005, "Name5", "LastName5"),
				new Student(10006, "Name5", "LastName5"));

		studentRepository.deleteById(100010);

		List<Student> actualStudentList = studentRepository.findAll();

		assertEquals(expectedStudentList, actualStudentList);
	}

	@Test
	void test_findAllByCouresName() {
		String courseName = "Physics";

		Group group1 = new Group(10001, "Group1");
		Group group2 = new Group(10002, "Group2");

		Course course2 = new Course(10002, "History",
				"History is the study of change over time, and it covers all aspects of human society.");
		Course course5 = new Course(10005, "Physics",
				"Physics is the branch of science that deals with the structure of matter and how the fundamental constituents of the universe interact.");
		Course course6 = new Course(10006, "Geography",
				"Geography is a branch of inquiry that focuses on spatial information on Earth.'");

		Student student1 = new Student(10001, group2, "Name1", "LastName1", List.of(course2, course5));
		Student student2 = new Student(10002, group1, "Name2", "LastName2", List.of(course5, course6));

		List<Student> expectedStudentList = List.of(student1, student2);

		List<Student> actualStudentList = studentRepository.findAllByCourseName(courseName);

		assertTrue(!actualStudentList.isEmpty());
		assertEquals(expectedStudentList, actualStudentList);
	}

	@Test
	void test_findAllByCouresName_NoSuchCourseName() {
		String courseName = "Physicss";

		List<Student> actualStudentList = studentRepository.findAllByCourseName(courseName);

		assertTrue(actualStudentList.isEmpty());
	}

	@Test
	void test_addStudentToCourse() {
		Course courseToAdd = new Course(10001, "Math",
				"Mathematics is the science and study of quality, structure, space, and change.");

		Optional<Student> student = studentRepository.findById(10001);

		assertTrue(student.isPresent());

		List<Course> expectedCourse = student.get().getCourses();
		expectedCourse.add(courseToAdd);
		Student expectedStudent = student.get();
		expectedStudent.setCourses(expectedCourse);

		studentRepository.addStudentToCourse(student.get(), courseToAdd);

		Optional<Student> actualStudent = studentRepository.findById(10001);

		assertTrue(actualStudent.isPresent());
		assertTrue(actualStudent.get().getCourses().contains(courseToAdd));
	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void test_removeStudentFromCourse() {
		Group group1 = new Group("AA-11");

		Course course1 = new Course("Course1", "descCourse1");
		Course course2 = new Course("Course2", "descCourse2");

		Student student = new Student(group1, "Name1", "LastName1", List.of(course1, course2));

		Student savedStudent = studentRepository.save(student);
		assertTrue(savedStudent.getCourses().contains(course2));

		Course courseForRemoving = savedStudent.getCourses().get(1);

		Student expectedStudent = new Student(savedStudent.getId(), group1, "Name1", "LastName1", List.of(course1));

		studentRepository.removeStudentFromCourse(savedStudent, courseForRemoving);
		Student actualStudent = studentRepository.findById(savedStudent.getId()).get();

		assertEquals(expectedStudent, actualStudent);
	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void test_removeStudentFromCourse_NoSuchCourse() {
		Group group1 = new Group("AA-11");

		Course course1 = new Course("Course1", "descCourse1");
		Course course2 = new Course("Course2", "descCourse2");
		Course course3 = new Course("Course3", "descCourse3");

		Student student = new Student(group1, "Name1", "LastName1", List.of(course1, course2));

		Student savedStudent = studentRepository.save(student);
		assertTrue(!savedStudent.getCourses().contains(course3));

		Course courseForRemoving = course3;

		studentRepository.removeStudentFromCourse(savedStudent, courseForRemoving);

		Student expectedStudent = new Student(savedStudent.getId(), group1, "Name1", "LastName1",
				List.of(course1, course2));
		Student actualStudent = studentRepository.findById(savedStudent.getId()).get();

		assertEquals(expectedStudent, actualStudent);
	}
}
