package com.foxminded.SchoolSpringJdbc.service.impl;

import org.springframework.stereotype.Service;

import com.foxminded.SchoolSpringJdbc.model.Course;
import com.foxminded.SchoolSpringJdbc.model.Group;
import com.foxminded.SchoolSpringJdbc.model.Student;
import com.foxminded.SchoolSpringJdbc.service.ConsoleService;
import com.foxminded.SchoolSpringJdbc.service.CourseService;
import com.foxminded.SchoolSpringJdbc.service.GroupService;
import com.foxminded.SchoolSpringJdbc.service.StudentService;
import com.foxminded.SchoolSpringJdbc.service.UserInputOutput;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsoleServiceImpl implements ConsoleService {

	private final UserInputOutput inputOutput;
	private final CourseService courseService;
	private final GroupService groupService;
	private final StudentService studentService;

	public ConsoleServiceImpl(UserInputOutputImpl inputOutput, CourseServiceImpl courseService,
			GroupServiceImpl groupService, StudentServiceImpl studentService) {
		this.inputOutput = inputOutput;
		this.courseService = courseService;
		this.groupService = groupService;
		this.studentService = studentService;
	}

	@Override
	public void startMenu() {
		String menu = """
				\nInput number of action:
				1. Find all the groups with less or equal student count
				2. Find all the students related to the course with the given name
				3. Add a new student
				4. Delete student by STUDENT_ID
				5. Add a student to the course (from the list)
				6. Remove the student from one of their courses
				7. exit\n""";
		while (true) {
			inputOutput.print(menu);
			Integer actionNumber = Integer.valueOf(inputOutput.read());
			if (actionNumber == 1) {
				inputOutput.print(findAllGroupsWithLessOrEqualStudentCount());
			}
			if (actionNumber == 2) {
				inputOutput.print(findAllStudentsByCourseName());
			}
			if (actionNumber == 3) {
				inputOutput.print(addNewStudent());
			}
			if (actionNumber == 4) {
				deleteStudentById();
			}
			if (actionNumber == 5) {
				addStudentToCourse();
			}
			if (actionNumber == 6) {
				removeStudentFromCourse();
			}
			if (actionNumber == 7) {
				break;
			}
		}
	}

	private String findAllGroupsWithLessOrEqualStudentCount() {
		log.info("Find all Groups with less or equal StudentCount");
		inputOutput.print("Student count =");
		Integer count = Integer.valueOf(inputOutput.read());

		return groupService.findAllByStudentsCount(count).toString();
	}

	public String findAllStudentsByCourseName() {
		log.info("Find all Students by CourseName");
		inputOutput.print("Course Name:");
		String name = inputOutput.read();

		return studentService.findAllByCourseName(name).toString();
	}

	private String addNewStudent() {
		log.info("Add New Student");
		int groupID;
		String name;
		String lastName;

		inputOutput.print("Student GroupID:");
		groupID = Integer.valueOf(inputOutput.read());
		inputOutput.print("Student Name:");
		name = inputOutput.read();
		inputOutput.print("Student LastName:");
		lastName = inputOutput.read();

		Group group = groupService.findById(groupID);
		Student student = new Student(group, name, lastName);

		student = studentService.save(student);

		return "Student [" + student.getGroupInfo() + ", firstname:" + student.getFirstName() + ", lastname:"
				+ student.getLastName() + ", Courses:" + student.getCoursesInfo() + "] saved";
	}

	private void deleteStudentById() {
		log.info("Delete Student by Id");
		inputOutput.print("Student ID:");
		Integer id = Integer.valueOf(inputOutput.read());

		studentService.deleteById(id);
	}

	private void addStudentToCourse() {
		log.info("Add Student to Course");
		inputOutput.print("Student ID:");
		Integer studentId = Integer.valueOf(inputOutput.read());
		inputOutput.print("Course ID:");
		Integer courseId = Integer.valueOf(inputOutput.read());

		Student student = studentService.findById(studentId);
		Course course = courseService.findById(courseId);

		if (student != null) {
			if (course != null) {
				studentService.addStudentToCourse(student, course);
			} else {
				log.warn("Сourse with ID={} doesn't exist", courseId);
			}
		} else {
			log.warn("Student with ID={} doesn't exist", studentId);
		}
	}

	private void removeStudentFromCourse() {
		log.info("Remove Student from Course");
		inputOutput.print("Student ID:");
		int studentId = Integer.valueOf(inputOutput.read());
		inputOutput.print("Course ID:");
		int courseId = Integer.valueOf(inputOutput.read());

		Student student = studentService.findById(studentId);
		Course course = courseService.findById(courseId);

		if (student != null) {
			if (course != null) {
				studentService.removeStudentFromCourse(student, course);
			} else {
				log.warn("Сourse with ID={} doesn't exist", courseId);
			}
		} else {
			log.warn("Student with ID={} doesn't exist", studentId);
		}
	}
}
