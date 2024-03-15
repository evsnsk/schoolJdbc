package com.foxminded.SchoolSpringJdbc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.foxminded.SchoolSpringJdbc.model.Course;
import com.foxminded.SchoolSpringJdbc.model.Group;
import com.foxminded.SchoolSpringJdbc.model.Student;
import com.foxminded.SchoolSpringJdbc.service.CourseService;
import com.foxminded.SchoolSpringJdbc.service.GeneratorService;
import com.foxminded.SchoolSpringJdbc.service.GroupService;
import com.foxminded.SchoolSpringJdbc.service.StudentService;
import com.foxminded.SchoolSpringJdbc.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GeneratorServiceImpl implements GeneratorService {

	private final GroupService groupService;
	private final CourseService courseService;
	private final StudentService studentService;

	@Value("${app.generator.group.count}")
	Integer groupCount;
	@Value("${app.generator.student.count}")
	Integer studentsCount;

	public GeneratorServiceImpl(GroupService groupService, CourseService courseService, StudentService studentService) {
		this.groupService = groupService;
		this.courseService = courseService;
		this.studentService = studentService;
	}

	@Override
	public void applyGenerator() {
		log.info("Generation is started");
		List<Group> groups = generateGroups();
		List<Student> students = generateStudents(groups);
		List<Course> courses = generateBasicCourses();
		assignStudentsToCourses(students, courses);
		log.info("Generation is finished");
	}

	public List<Group> generateGroups() {
		log.info("Groups generation starting");
		List<Group> groups = new ArrayList<>();
		List<String> groupNames = generateGroupsNames(this.groupCount);
		groupNames.stream().forEach(name -> groups.add(groupService.save(new Group(name))));
		log.info("Groups were generated (count={})", this.groupCount);
		return groups;
	}

	private List<String> generateGroupsNames(int groupCount) {
		log.info("Groups NAMES generation starting");
		List<String> groupNames = new ArrayList<>();
		for (int i = 1; i <= groupCount; i++) {
			groupNames.add(
					StringUtils.generateRandomAlphabeticString(2) + "-" + StringUtils.generateRandomNumericString(2));
		}
		log.info("Groups NAMES were generated (count={})", groupCount);
		return groupNames;
	}

	public List<Course> generateBasicCourses() {
		log.info("Courses generation starting");
		List<Course> savedBasicCourses = new ArrayList<>();
		List<Course> basicCourses = List.of(
				new Course("Math", "Mathematics is the science and study of quality, structure, space, and change."),
				new Course("History",
						"History is the study of change over time, and it covers all aspects of human society."),
				new Course("Music",
						"Music is an art of sound in time that expresses ideas and emotions in significant forms through the elements of rhythm, melody, harmony, and color."),
				new Course("PE",
						"Physical education provides cognitive content and instruction designed to develop motor skills, knowledge, and behaviors for physical activity and physical fitness."),
				new Course("Physics",
						"Physics is the branch of science that deals with the structure of matter and how the fundamental constituents of the universe interact."),
				new Course("Geography",
						"Geography is a branch of inquiry that focuses on spatial information on Earth."),
				new Course("Informatics",
						"Informatics involves the practice of information processing and the engineering of information systems."),
				new Course("Engeneering",
						"The practical and innovative application of maths and sciences will be used to design, develop and maintain infrastructures, products and systems on a large scale."),
				new Course("Law", "The subject of law is the holder of the rights and obligations under the law."),
				new Course("Social Studies and Media",
						"You will study how we as human beings interact as part of society."));
		basicCourses.stream().forEach(course -> {
			Course savedCourse = courseService.save(course);
			savedBasicCourses.add(savedCourse);
		});
		log.info("Courses were generated (count={})", basicCourses.size());
		return savedBasicCourses;
	}

	public List<Student> generateStudents(List<Group> groups) {
		log.info("Students generation starting");
		List<Student> students = new ArrayList<>();
		List<Student> savedStudents = new ArrayList<>();
		for (int i = 0; i < studentsCount; i++) {
			Student student = new Student();
			student.setFirstName(generateFirstName());
			student.setLastName(generateLastName());
			students.add(student);
		}
		List<Student> assignedStudents = assignStudentsToGroups(students, groups);
		assignedStudents.stream().forEach(it -> savedStudents.add(studentService.save(it)));
		log.info("Students were generated (count={})", savedStudents.size());
		return savedStudents;
	}

	private String generateFirstName() {
		List<String> firstNames = List.of("Theodore", "Charlotte", "Avery", "Eden", "Jude", "Asher", "Oliver", "Lucy",
				"Eira", "River", "Amelia", "David", "Luna", "Noah", "Henry", "Lucas", "Anna", "Sophia", "Emma", "Mia");
		return firstNames.get(StringUtils.generateRandomNumber(0, firstNames.size() - 1));
	}

	private String generateLastName() {
		List<String> lastNames = List.of("Elsher", "Solace", "Levine", "Thatcher", "Raven", "Bardot", "Hansley",
				"Ashley", "West", "Hope", "Adler", "Ford", "Finnegan", "Zimmerman", "Wilson", "Gray", "Anderson",
				"Adams", "Ashford", "Dagon");
		return lastNames.get(StringUtils.generateRandomNumber(0, lastNames.size() - 1));
	}

	public List<Student> assignStudentsToGroups(List<Student> students, List<Group> groups) {
		log.info("Students assigning to Groups starting");
		List<Student> assignedStudents = new ArrayList<>();

		if (students.size() >= 10) {
			int studentIndex = 0;
			for (Group group : groups) {
				if (studentIndex >= students.size()) {
					break;
				}
				int randonStudentAmount = StringUtils.generateRandomNumber(10, 30);
				if (randonStudentAmount > students.size() - studentIndex) {
					randonStudentAmount = students.size() - studentIndex;
				}
				for (int student = studentIndex; student < (studentIndex + randonStudentAmount); student++) {
					if (students.size() - studentIndex > 10) {
						students.get(student).setGroup(group);
					}
					assignedStudents.add(students.get(student));
				}
				studentIndex += randonStudentAmount;
			}
			log.info("Students were assigned to Groups");
			return assignedStudents;
		} else {
			log.warn("Students count <10, Assigning is not allowed");
			return students;
		}
	}

	public Map<Integer, Integer> assignStudentsToCourses(List<Student> students, List<Course> courses) {
		log.info("Students assigning to Courses starting");
		Map<Integer, Integer> studentsCourses = new HashMap<>();
		for (Student student : students) {
			int randonCourseAmount = StringUtils.generateRandomNumber(1, 3);
			List<Course> randomCourses = new ArrayList<>();

			while (randomCourses.size() < randonCourseAmount) {
				int randomCourseId = StringUtils.generateRandomNumber(0, courses.size() - 1);
				if (!randomCourses.contains(courses.get(randomCourseId))) {
					randomCourses.add(courses.get(randomCourseId));
				}
			}
			randomCourses.stream().forEach(course -> {
				studentService.addStudentToCourse(student, course);
				studentsCourses.put(student.getId(), course.getId());
			});
		}
		log.info("Students were assigned to Courses");
		return studentsCourses;
	}

	public int getGroupCount() {
		return groupCount;
	}

	public int getStudentsCount() {
		return studentsCount;
	}

}
