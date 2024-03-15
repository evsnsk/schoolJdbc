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

import com.foxminded.SchoolSpringJdbc.model.Group;
import com.foxminded.SchoolSpringJdbc.model.Student;

@ActiveProfiles("test")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		GroupRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/clear_tables.sql",
		"/sql/sample_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GroupRepositoryTest {

	@Autowired
	GroupRepository groupRepository;

	@Test
	void test_findById() {
		Group expected = new Group(10001, "Group1");
		Optional<Group> actual = groupRepository.findById(10001);

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}

	@Test
	void test_findById_NoSuchId() {
		Optional<Group> actual = groupRepository.findById(-1);

		assertTrue(actual.isEmpty());
	}

	@Test
	void test_findByName() {
		Group expected = new Group(10002, "Group2");
		Optional<Group> actual = groupRepository.findByName(expected.getName());

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}

	@Test
	void test_findByName_NoSuchName() {
		String name = "Group22";

		Optional<Group> actual = groupRepository.findByName(name);

		assertTrue(actual.isEmpty());
	}

	@Test
	void test_findAll() {
		List<Group> expected = List.of(new Group(10001, "Group1"), new Group(10002, "Group2"),
				new Group(10003, "Group3"));
		List<Group> actual = groupRepository.findAll();

		assertTrue(!actual.isEmpty());
		assertEquals(expected, actual);
	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void test_getAll_NoData() {
		List<Group> actual = groupRepository.findAll();

		assertTrue(actual.isEmpty());
	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void test_save_EmptyId() {
		Group group = new Group();
		group.setName("Group4");

		groupRepository.save(group);

		List<Group> expectedGroupList = List.of(group);
		List<Group> actualGroupList = groupRepository.findAll();
		Optional<Group> actualGroup = groupRepository.findById(group.getId());

		assertTrue(actualGroup.isPresent());
		assertEquals(expectedGroupList, actualGroupList);
	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void test_save_NoSuchId() {
		Group group = new Group(null, "GroupNEW");

		Group actualGroup = groupRepository.save(group);
		Group expectedGroup = group;

		expectedGroup.setId(actualGroup.getId());

		List<Group> expectedGroupList = List.of(group);
		List<Group> actualGroupList = groupRepository.findAll();

		assertEquals(expectedGroup, actualGroup);
		assertEquals(expectedGroupList, actualGroupList);
	}

	@Test
	void test_save_ById() {
		Group group = new Group(null, "GroupNEW");

		groupRepository.save(group);
		group.setName("GroupNEW2");
		Group actualGroup = group;

		Group expectedGroup = new Group(group.getId(), "GroupNEW2");

		expectedGroup.setId(actualGroup.getId());

		List<Group> expectedGroupList = List.of(new Group(10001, "Group1"), new Group(10002, "Group2"),
				new Group(10003, "Group3"), expectedGroup);
		List<Group> actualGroupList = groupRepository.findAll();

		assertEquals(expectedGroup, actualGroup);
		assertEquals(expectedGroupList, actualGroupList);
	}

	@Test
	void test_delete() {
		List<Group> expectedGroupList = List.of(new Group(10001, "Group1"), new Group(10002, "Group2"));

		groupRepository.deleteById(10003);

		Optional<Group> actualGroup = groupRepository.findById(10003);

		List<Group> actualGroupList = groupRepository.findAll();

		assertTrue(actualGroup.isEmpty());
		assertEquals(expectedGroupList, actualGroupList);

	}

	@Test
	void test_delete_NoSuchId() {
		groupRepository.deleteById(100010);

		List<Group> expected = List.of(new Group(10001, "Group1"), new Group(10002, "Group2"),
				new Group(10003, "Group3"));
		List<Group> actual = groupRepository.findAll();

		assertEquals(expected, actual);
	}

	@Test
	void test_findAllByStudentsCount() {
		int maxStudentCount = 2;

		Student student1 = new Student(10001, "Name1", "LName1");
		Student student2 = new Student(10002, "Name2", "LName2");
		Student student3 = new Student(10003, "Name3", "LName3");

		Group group1 = new Group(10001, "Group1", List.of(student1));
		Group group2 = new Group(10002, "Group2", List.of(student2, student3));

		List<Group> expected = List.of(group1, group2);

		List<Group> actual = groupRepository.findAllByStudentsCountLessOrEqual(maxStudentCount);

		assertTrue(!actual.isEmpty());
		assertEquals(expected, actual);
	}

}
