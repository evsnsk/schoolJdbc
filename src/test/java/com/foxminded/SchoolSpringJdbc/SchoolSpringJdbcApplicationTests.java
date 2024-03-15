package com.foxminded.SchoolSpringJdbc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.foxminded.SchoolSpringJdbc.service.impl.ConsoleServiceImpl;
import com.foxminded.SchoolSpringJdbc.service.impl.UserInputOutputImpl;

@ActiveProfiles({ "test", "disableGenerator", "disableMenu" })
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/clear_tables.sql",
		"/sql/sample_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class SchoolSpringJdbcApplicationTests {
	@MockBean
	UserInputOutputImpl inputOutput;

	@Autowired
	ConsoleServiceImpl consoleService;

	@Captor
	private ArgumentCaptor<String> stringArgumentCaptor;

	@Test
	@Timeout(100)
	void contextLoads() {

		when(inputOutput.read()).thenReturn("3", "10001", "N1", "LN1", "1", "2", "5", "10003", "10001", "5", "10002",
				"10001", "2", "Math", "2", "Mathh", "6", "10002", "10001", "2", "Math", "1", "1", "4", "10003", "2",
				"PE", "7");

		consoleService.startMenu();

		verify(inputOutput, atLeast(1)).print(stringArgumentCaptor.capture());
		verify(inputOutput, atLeast(1)).read();
		String expected = """

				Input number of action:
				1. Find all the groups with less or equal student count
				2. Find all the students related to the course with the given name
				3. Add a new student
				4. Delete student by STUDENT_ID
				5. Add a student to the course (from the list)
				6. Remove the student from one of their courses
				7. exit

				Student GroupID:
				Student Name:
				Student LastName:
				Student [Group: Group1, firstname:N1, lastname:LN1, Courses:No courses] saved

				Input number of action:
				1. Find all the groups with less or equal student count
				2. Find all the students related to the course with the given name
				3. Add a new student
				4. Delete student by STUDENT_ID
				5. Add a student to the course (from the list)
				6. Remove the student from one of their courses
				7. exit

				Student count =
				[Group [id=10001, name=Group1, students=Students: Name2 LastName2, N1 LN1], Group [id=10002, name=Group2, students=Students: Name1 LastName1, Name5 LastName5]]

				Input number of action:
				1. Find all the groups with less or equal student count
				2. Find all the students related to the course with the given name
				3. Add a new student
				4. Delete student by STUDENT_ID
				5. Add a student to the course (from the list)
				6. Remove the student from one of their courses
				7. exit

				Student ID:
				Course ID:

				Input number of action:
				1. Find all the groups with less or equal student count
				2. Find all the students related to the course with the given name
				3. Add a new student
				4. Delete student by STUDENT_ID
				5. Add a student to the course (from the list)
				6. Remove the student from one of their courses
				7. exit

				Student ID:
				Course ID:

				Input number of action:
				1. Find all the groups with less or equal student count
				2. Find all the students related to the course with the given name
				3. Add a new student
				4. Delete student by STUDENT_ID
				5. Add a student to the course (from the list)
				6. Remove the student from one of their courses
				7. exit

				Course Name:
				[Student [id=10003, group=Group: Group3, firstName=Name3, lastName=LastName3, courses=Courses: Math, PE], Student [id=10002, group=Group: Group1, firstName=Name2, lastName=LastName2, courses=Courses: Math, Physics, Geography]]

				Input number of action:
				1. Find all the groups with less or equal student count
				2. Find all the students related to the course with the given name
				3. Add a new student
				4. Delete student by STUDENT_ID
				5. Add a student to the course (from the list)
				6. Remove the student from one of their courses
				7. exit

				Course Name:
				[]

				Input number of action:
				1. Find all the groups with less or equal student count
				2. Find all the students related to the course with the given name
				3. Add a new student
				4. Delete student by STUDENT_ID
				5. Add a student to the course (from the list)
				6. Remove the student from one of their courses
				7. exit

				Student ID:
				Course ID:

				Input number of action:
				1. Find all the groups with less or equal student count
				2. Find all the students related to the course with the given name
				3. Add a new student
				4. Delete student by STUDENT_ID
				5. Add a student to the course (from the list)
				6. Remove the student from one of their courses
				7. exit

				Course Name:
				[Student [id=10003, group=Group: Group3, firstName=Name3, lastName=LastName3, courses=Courses: Math, PE]]

				Input number of action:
				1. Find all the groups with less or equal student count
				2. Find all the students related to the course with the given name
				3. Add a new student
				4. Delete student by STUDENT_ID
				5. Add a student to the course (from the list)
				6. Remove the student from one of their courses
				7. exit

				Student count =
				[]

				Input number of action:
				1. Find all the groups with less or equal student count
				2. Find all the students related to the course with the given name
				3. Add a new student
				4. Delete student by STUDENT_ID
				5. Add a student to the course (from the list)
				6. Remove the student from one of their courses
				7. exit

				Student ID:

				Input number of action:
				1. Find all the groups with less or equal student count
				2. Find all the students related to the course with the given name
				3. Add a new student
				4. Delete student by STUDENT_ID
				5. Add a student to the course (from the list)
				6. Remove the student from one of their courses
				7. exit

				Course Name:
				[Student [id=10004, group=Group: Group3, firstName=Name4, lastName=LastName4, courses=Courses: PE], Student [id=10003, group=No group, firstName=Name3, lastName=LastName3, courses=Courses: Math, PE]]

				Input number of action:
				1. Find all the groups with less or equal student count
				2. Find all the students related to the course with the given name
				3. Add a new student
				4. Delete student by STUDENT_ID
				5. Add a student to the course (from the list)
				6. Remove the student from one of their courses
				7. exit
				""";
		String actual = stringArgumentCaptor.getAllValues().stream().collect(Collectors.joining("\n"));

		System.out.print(actual);

		assertEquals(expected, actual);
	}

}
