package com.foxminded.SchoolSpringJdbc.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.foxminded.SchoolSpringJdbc.model.Course;
import com.foxminded.SchoolSpringJdbc.model.Group;
import com.foxminded.SchoolSpringJdbc.model.Student;
import com.foxminded.SchoolSpringJdbc.service.CourseService;
import com.foxminded.SchoolSpringJdbc.service.GroupService;
import com.foxminded.SchoolSpringJdbc.service.StudentService;
import com.foxminded.SchoolSpringJdbc.utils.StringUtils;

@ActiveProfiles("test")
@SpringBootTest(classes = { GeneratorServiceImpl.class })
class GeneratorServiceImplTest {

	@MockBean
	GroupService groupService;
	@MockBean
	CourseService courseService;
	@MockBean
	StudentService studentService;

	@Autowired
	GeneratorServiceImpl generatorService;

	@Test
	void test_generateGroups() {
		Group expectedGropup = new Group(1, "aa-11");

		when(groupService.save(any(Group.class))).thenReturn(expectedGropup);

		List<Group> expectedGroups = List.of(expectedGropup, expectedGropup);

		List<Group> actualGroups = generatorService.generateGroups();

		assertTrue(!actualGroups.isEmpty());
		assertEquals(expectedGroups, actualGroups);

	}

	@Test
	void test_generateBasicCourses() {
		Course course = new Course("Math",
				"Mathematics is the science and study of quality, structure, space, and change.");
		List<Course> expected = List.of(course, course, course, course, course, course, course, course, course, course);

		when(courseService.save(any(Course.class))).thenReturn(course);

		List<Course> actual = generatorService.generateBasicCourses();

		verify(courseService, atLeast(10)).save(any(Course.class));

		assertTrue(!actual.isEmpty());
		assertEquals(expected, actual);

	}

	@Test
	void test_generateStudents() {
		List<Group> groups = List.of(new Group(10, "aa-10"));

		Student expectedStudent = new Student(1, new Group(10, "aa-10"), "Theodore", "Elsher");
		List<Student> expectedStudents = List.of(expectedStudent, expectedStudent, expectedStudent, expectedStudent,
				expectedStudent, expectedStudent, expectedStudent, expectedStudent, expectedStudent, expectedStudent);

		MockedStatic<StringUtils> mockedStringUtils = mockStatic(StringUtils.class);
		mockedStringUtils.when(() -> StringUtils.generateRandomNumber(0, 19)).thenReturn(0);
		mockedStringUtils.when(() -> StringUtils.generateRandomNumber(10, 30)).thenReturn(10);
		assertEquals(0, StringUtils.generateRandomNumber(0, 19));

		when(studentService.save(any(Student.class))).thenReturn(expectedStudent);

		List<Student> actualStudents = generatorService.generateStudents(groups);

		mockedStringUtils.close();

		verify(studentService, times(10)).save(any(Student.class));

		assertTrue(!actualStudents.isEmpty());
		assertEquals(expectedStudents, actualStudents);

	}

	@Test
	void test_assignStudentsToGroups() {
		Group group1 = new Group(20001, "aa-11");
		Group group2 = new Group(20002, "bb-11");
		List<Group> groups = List.of(group1, group2);

		Student student1 = new Student();
		student1.setId(10001);
		student1.setFirstName("Theodore");
		student1.setLastName("Elsher");

		Student student2 = new Student();
		student2.setId(10002);
		student2.setFirstName("Theodore");
		student2.setLastName("Elsher");

		Student student3 = new Student();
		student3.setId(10003);
		student3.setFirstName("Theodore");
		student3.setLastName("Elsher");

		Student student4 = new Student();
		student4.setId(10004);
		student4.setFirstName("Theodore");
		student4.setLastName("Elsher");

		Student student5 = new Student();
		student5.setId(10005);
		student5.setFirstName("Theodore");
		student5.setLastName("Elsher");

		Student student6 = new Student();
		student6.setId(10006);
		student6.setFirstName("Theodore");
		student6.setLastName("Elsher");

		Student student7 = new Student();
		student7.setId(10007);
		student7.setFirstName("Theodore");
		student7.setLastName("Elsher");

		Student student8 = new Student();
		student8.setId(10008);
		student8.setFirstName("Theodore");
		student8.setLastName("Elsher");

		Student student9 = new Student();
		student9.setId(10009);
		student9.setFirstName("Theodore");
		student9.setLastName("Elsher");

		Student student10 = new Student();
		student10.setId(10010);
		student10.setFirstName("Theodore");
		student10.setLastName("Elsher");

		Student student11 = new Student();
		student11.setId(10011);
		student11.setFirstName("Theodore");
		student11.setLastName("Elsher");

		List<Student> students = List.of(student1, student2, student3, student4, student5, student6, student7, student8,
				student9, student10, student11);

		Student expectedStudent1 = new Student(10001, new Group(20001, "aa-11"), "Theodore", "Elsher");
		Student expectedStudent2 = new Student(10002, new Group(20001, "aa-11"), "Theodore", "Elsher");
		Student expectedStudent3 = new Student(10003, new Group(20001, "aa-11"), "Theodore", "Elsher");
		Student expectedStudent4 = new Student(10004, new Group(20001, "aa-11"), "Theodore", "Elsher");
		Student expectedStudent5 = new Student(10005, new Group(20001, "aa-11"), "Theodore", "Elsher");
		Student expectedStudent6 = new Student(10006, new Group(20001, "aa-11"), "Theodore", "Elsher");
		Student expectedStudent7 = new Student(10007, new Group(20001, "aa-11"), "Theodore", "Elsher");
		Student expectedStudent8 = new Student(10008, new Group(20001, "aa-11"), "Theodore", "Elsher");
		Student expectedStudent9 = new Student(10009, new Group(20001, "aa-11"), "Theodore", "Elsher");
		Student expectedStudent10 = new Student(10010, new Group(20001, "aa-11"), "Theodore", "Elsher");

		Student studentWithoutGroup = new Student();
		studentWithoutGroup.setId(10011);
		studentWithoutGroup.setFirstName("Theodore");
		studentWithoutGroup.setLastName("Elsher");

		List<Student> expectedStudents = List.of(expectedStudent1, expectedStudent2, expectedStudent3, expectedStudent4,
				expectedStudent5, expectedStudent6, expectedStudent7, expectedStudent8, expectedStudent9,
				expectedStudent10, studentWithoutGroup);

		MockedStatic<StringUtils> mockedStringUtils = mockStatic(StringUtils.class);
		mockedStringUtils.when(() -> StringUtils.generateRandomNumber(10, 30)).thenReturn(10);

		List<Student> actualStudents = generatorService.assignStudentsToGroups(students, groups);

		mockedStringUtils.close();

		assertTrue(!actualStudents.isEmpty());
		assertEquals(expectedStudents, actualStudents);

	}

	@Test
	void test_assignStudentsToCourses() {
		Map<Integer, Integer> expectedStudentsCourses = Map.of(1, 8, 2, 8, 3, 8, 4, 8, 5, 8, 6, 8, 7, 8, 8, 8, 9, 8, 10,
				8);
		List<Course> courses = List.of(new Course(8, "Math",
				"Mathematics is the science and study of quality, structure, space, and change."));
		Group group = new Group(1, "aa-11");

		Student student1 = new Student(1, group, "Theodore", "Elsher");
		Student student2 = new Student(2, group, "Theodore", "Elsher");
		Student student3 = new Student(3, group, "Theodore", "Elsher");
		Student student4 = new Student(4, group, "Theodore", "Elsher");
		Student student5 = new Student(5, group, "Theodore", "Elsher");
		Student student6 = new Student(6, group, "Theodore", "Elsher");
		Student student7 = new Student(7, group, "Theodore", "Elsher");
		Student student8 = new Student(8, group, "Theodore", "Elsher");
		Student student9 = new Student(9, group, "Theodore", "Elsher");
		Student student10 = new Student(10, group, "Theodore", "Elsher");

		List<Student> students = List.of(student1, student2, student3, student4, student5, student6, student7, student8,
				student9, student10);

		MockedStatic<StringUtils> mockedStringUtils = mockStatic(StringUtils.class);
		mockedStringUtils.when(() -> StringUtils.generateRandomNumber(1, 3)).thenReturn(1);

		Map<Integer, Integer> actualStudentsCourses = generatorService.assignStudentsToCourses(students, courses);

		mockedStringUtils.close();

		assertTrue(!actualStudentsCourses.isEmpty());
		assertEquals(expectedStudentsCourses, actualStudentsCourses);

	}

}