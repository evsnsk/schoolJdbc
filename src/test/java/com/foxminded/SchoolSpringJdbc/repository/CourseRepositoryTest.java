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

@ActiveProfiles("test")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		CourseRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/clear_tables.sql",
		"/sql/sample_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CourseRepositoryTest {

	@Autowired
	CourseRepository courseRepository;

	@Test
	void test_findById() {
		Course expected = new Course(10001, "Math",
				"Mathematics is the science and study of quality, structure, space, and change.");
		Optional<Course> actual = courseRepository.findById(10001);

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}

	@Test
	void test_findById_NoSuchId() {
		Optional<Course> actual = courseRepository.findById(-1);

		assertTrue(actual.isEmpty());
	}

	@Test
	void test_findByName() {
		Course expected = new Course(10003, "Music",
				"Music is an art of sound in time that expresses ideas and emotions in significant forms through the elements of rhythm, melody, harmony, and color.");

		Optional<Course> actual = courseRepository.findByName(expected.getName());

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}

	@Test
	void test_findByName_NoSuchName() {
		String name = "Musiic";

		Optional<Course> actual = courseRepository.findByName(name);

		assertTrue(actual.isEmpty());
	}

	@Test
	void test_findAll() {
		List<Course> expected = List.of(
				new Course(10001, "Math",
						"Mathematics is the science and study of quality, structure, space, and change."),
				new Course(10002, "History",
						"History is the study of change over time, and it covers all aspects of human society."),
				new Course(10003, "Music",
						"Music is an art of sound in time that expresses ideas and emotions in significant forms through the elements of rhythm, melody, harmony, and color."),
				new Course(10004, "PE",
						"Physical education provides cognitive content and instruction designed to develop motor skills, knowledge, and behaviors for physical activity and physical fitness."),
				new Course(10005, "Physics",
						"Physics is the branch of science that deals with the structure of matter and how the fundamental constituents of the universe interact."),
				new Course(10006, "Geography",
						"Geography is a branch of inquiry that focuses on spatial information on Earth."),
				new Course(10007, "Informatics",
						"Informatics involves the practice of information processing and the engineering of information systems. "));
		List<Course> actual = courseRepository.findAll();

		assertTrue(!actual.isEmpty());
		assertEquals(expected, actual);
	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void test_findAll_NoData() {
		List<Course> actual = courseRepository.findAll();

		assertTrue(actual.isEmpty());
	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void test_save_EmptyId() {
		Course course = new Course();
		course.setName("Biology");
		course.setDescription(
				"Biology is the science of life. It spans multiple levels from biomolecules and cells to organisms and populations.");

		courseRepository.save(course);

		List<Course> expectedList = List.of(course);
		Optional<Course> actualCourse = courseRepository.findById(course.getId());
		List<Course> actualCourseList = courseRepository.findAll();

		assertTrue(actualCourse.isPresent());
		assertEquals(expectedList, actualCourseList);

	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void test_save_NoSuchId() {
		Course course = new Course(null, "Biology",
				"Biology is the science of life. It spans multiple levels from biomolecules and cells to organisms and populations.");
		Course actualCourse = courseRepository.save(course);
		Course expetedCourse = course;

		expetedCourse.setId(actualCourse.getId());

		List<Course> expectedList = List.of(expetedCourse);
		List<Course> actualCourseList = courseRepository.findAll();

		assertEquals(expetedCourse, actualCourse);
		assertEquals(expectedList, actualCourseList);
	}

	@Test
	void test_save_ById() {
		Course course = new Course(null, "Biology",
				"Biology is the science of life. It spans multiple levels from biomolecules and cells to organisms and populations.");

		courseRepository.save(course);
		course.setName("111111");

		List<Course> expectedList = List.of(
				new Course(10001, "Math",
						"Mathematics is the science and study of quality, structure, space, and change."),
				new Course(10002, "History",
						"History is the study of change over time, and it covers all aspects of human society."),
				new Course(10003, "Music",
						"Music is an art of sound in time that expresses ideas and emotions in significant forms through the elements of rhythm, melody, harmony, and color."),
				new Course(10004, "PE",
						"Physical education provides cognitive content and instruction designed to develop motor skills, knowledge, and behaviors for physical activity and physical fitness."),
				new Course(10005, "Physics",
						"Physics is the branch of science that deals with the structure of matter and how the fundamental constituents of the universe interact."),
				new Course(10006, "Geography",
						"Geography is a branch of inquiry that focuses on spatial information on Earth."),
				new Course(10007, "Informatics",
						"Informatics involves the practice of information processing and the engineering of information systems. "),
				course);
		Optional<Course> actualCourse = courseRepository.findById(course.getId());
		List<Course> actualCourseList = courseRepository.findAll();

		assertTrue(actualCourse.isPresent());
		assertEquals(expectedList, actualCourseList);
	}

	@Test
	void test_delete() {
		List<Course> expectedList = List.of(
				new Course(10001, "Math",
						"Mathematics is the science and study of quality, structure, space, and change."),
				new Course(10002, "History",
						"History is the study of change over time, and it covers all aspects of human society."),
				new Course(10004, "PE",
						"Physical education provides cognitive content and instruction designed to develop motor skills, knowledge, and behaviors for physical activity and physical fitness."),
				new Course(10005, "Physics",
						"Physics is the branch of science that deals with the structure of matter and how the fundamental constituents of the universe interact."),
				new Course(10006, "Geography",
						"Geography is a branch of inquiry that focuses on spatial information on Earth."),
				new Course(10007, "Informatics",
						"Informatics involves the practice of information processing and the engineering of information systems. "));

		courseRepository.deleteById(10003);

		Optional<Course> actualCourse = courseRepository.findById(10003);
		List<Course> actualCourseList = courseRepository.findAll();

		assertTrue(actualCourse.isEmpty());
		assertEquals(expectedList, actualCourseList);
	}

	@Test
	void test_delete_NoSuchId() {
		List<Course> expected = List.of(
				new Course(10001, "Math",
						"Mathematics is the science and study of quality, structure, space, and change."),
				new Course(10002, "History",
						"History is the study of change over time, and it covers all aspects of human society."),
				new Course(10003, "Music",
						"Music is an art of sound in time that expresses ideas and emotions in significant forms through the elements of rhythm, melody, harmony, and color."),
				new Course(10004, "PE",
						"Physical education provides cognitive content and instruction designed to develop motor skills, knowledge, and behaviors for physical activity and physical fitness."),
				new Course(10005, "Physics",
						"Physics is the branch of science that deals with the structure of matter and how the fundamental constituents of the universe interact."),
				new Course(10006, "Geography",
						"Geography is a branch of inquiry that focuses on spatial information on Earth."),
				new Course(10007, "Informatics",
						"Informatics involves the practice of information processing and the engineering of information systems. "));

		courseRepository.deleteById(100010);
		List<Course> actual = courseRepository.findAll();

		assertEquals(expected, actual);
	}

	@Test
	void test_findAllByStudentId() {
		int studentId = 10001;
		List<Course> expected = List.of(
				new Course(10002, "History",
						"History is the study of change over time, and it covers all aspects of human society."),
				new Course(10005, "Physics",
						"Physics is the branch of science that deals with the structure of matter and how the fundamental constituents of the universe interact."));

		List<Course> actual = courseRepository.findAllByStudentId(studentId);

		assertTrue(!actual.isEmpty());

		expected.get(0).setId(actual.get(0).getId());

		assertEquals(expected, actual);
	}

	@Test
	void test_findAllByStudentId_NoSuchStudent() {
		int studentId = -1;
		List<Course> actual = courseRepository.findAllByStudentId(studentId);

		assertTrue(actual.isEmpty());
	}

}
