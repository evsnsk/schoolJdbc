package com.foxminded.SchoolSpringJdbc.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.foxminded.SchoolSpringJdbc.model.Course;
import com.foxminded.SchoolSpringJdbc.repository.CourseRepository;
import com.foxminded.SchoolSpringJdbc.service.CourseService;

@ActiveProfiles("test")
@SpringBootTest(classes = { CourseServiceImpl.class })
class CourseServiceImplTest {

	@MockBean
	CourseRepository courseRepository;

	@Autowired
	CourseService courseService;

	@Test
	void test_findById() {
		int courseId = 3;
		Course expected = new Course(3, "Music",
				"Music is an art of sound in time that expresses ideas and emotions in significant forms through the elements of rhythm, melody, harmony, and color.");
		Optional<Course> course = Optional.of(expected);

		when(courseRepository.findById(courseId)).thenReturn(course);

		Course actual = courseService.findById(courseId);

		verify(courseRepository).findById(courseId);
		assertEquals(expected, actual);
	}

	@Test
	void test_findById_NoSuchId() {
		int courseId = -1;
		Optional<Course> course = Optional.empty();

		when(courseRepository.findById(courseId)).thenReturn(course);

		Course actual = courseService.findById(courseId);

		verify(courseRepository).findById(courseId);
		assertEquals(null, actual);
	}

	@Test
	void test_findByName() {
		String name = "Music";
		Optional<Course> expected = Optional.of(new Course(3, "Music",
				"Music is an art of sound in time that expresses ideas and emotions in significant forms through the elements of rhythm, melody, harmony, and color."));

		when(courseRepository.findByName(name)).thenReturn(expected);

		Course actual = courseService.findByName(name);

		verify(courseRepository).findByName(name);
		assertEquals(expected.get(), actual);
	}

	@Test
	void test_findByName_NoSuchName() {
		String name = "Musicc";
		Optional<Course> course = Optional.empty();

		when(courseRepository.findByName(name)).thenReturn(course);

		Course actual = courseService.findByName(name);

		verify(courseRepository).findByName(name);
		assertEquals(null, actual);
	}

	@Test
	void test_findAll() {
		List<Course> expected = List.of(
				new Course(1, "Math", "Mathematics is the science and study of quality, structure, space, and change."),
				new Course(2, "History",
						"History is the study of change over time, and it covers all aspects of human society."),
				new Course(3, "Music",
						"Music is an art of sound in time that expresses ideas and emotions in significant forms through the elements of rhythm, melody, harmony, and color."),
				new Course(4, "PE",
						"Physical education provides cognitive content and instruction designed to develop motor skills, knowledge, and behaviors for physical activity and physical fitness."),
				new Course(5, "Physics",
						"Physics is the branch of science that deals with the structure of matter and how the fundamental constituents of the universe interact."),
				new Course(6, "Geography",
						"Geography is a branch of inquiry that focuses on spatial information on Earth."),
				new Course(7, "Informatics",
						"Informatics involves the practice of information processing and the engineering of information systems. "));

		when(courseRepository.findAll()).thenReturn(expected);

		List<Course> actual = courseService.findAll();

		verify(courseRepository).findAll();
		assertEquals(expected, actual);
	}

	@Test
	void test_findAll_EmptyDB() {
		List<Course> expected = new ArrayList<>();

		when(courseRepository.findAll()).thenReturn(new ArrayList<>());

		List<Course> actual = courseService.findAll();

		verify(courseRepository).findAll();
		assertEquals(expected, actual);
	}

	@Test
	void test_save() {
		Course expected = new Course(1, "Math",
				"Mathematics is the science and study of quality, structure, space, and change.");
		Course course = new Course();
		course.setName("Math");
		course.setDescription("Mathematics is the science and study of quality, structure, space, and change.");

		when(courseRepository.save(course)).thenReturn(expected);

		Course actual = courseService.save(course);

		verify(courseRepository).save(course);
		assertEquals(expected, actual);
	}

	@Test
	void test_deleteById() {
		int courseId = 3;

		courseService.deleteById(courseId);

		verify(courseRepository).deleteById(courseId);

	}

	@Test
	void test_findAllByStudentId() {
		int studentId = 2;
		List<Course> expected = List.of(new Course(5, "Physics",
				"Physics is the branch of science that deals with the structure of matter and how the fundamental constituents of the universe interact."),
				new Course(6, "Geography",
						"Geography is a branch of inquiry that focuses on spatial information on Earth."));

		when(courseRepository.findAllByStudentId(studentId)).thenReturn(expected);

		List<Course> actual = courseService.findAllByStudentId(studentId);

		verify(courseRepository).findAllByStudentId(studentId);
		assertEquals(expected, actual);
	}

	@Test
	void test_findAllByStudentId_NoSuchId() {
		int studentId = -1;
		List<Course> expected = new ArrayList<>();

		when(courseRepository.findAllByStudentId(studentId)).thenReturn(expected);

		List<Course> actual = courseService.findAllByStudentId(studentId);

		verify(courseRepository).findAllByStudentId(studentId);
		assertEquals(expected, actual);
	}

}
