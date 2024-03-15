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

import com.foxminded.SchoolSpringJdbc.model.Group;
import com.foxminded.SchoolSpringJdbc.repository.GroupRepository;
import com.foxminded.SchoolSpringJdbc.service.GroupService;

@ActiveProfiles("test")
@SpringBootTest(classes = { GroupServiceImpl.class })
class GroupServiceImplTest {

	@MockBean
	GroupRepository groupRepository;

	@Autowired
	GroupService groupService;

	@Test
	void test_findById() {
		int groupId = 1;
		Group expected = new Group(1, "Group1");
		Optional<Group> group = Optional.of(expected);

		when(groupRepository.findById(groupId)).thenReturn(group);

		Group actual = groupService.findById(groupId);

		verify(groupRepository).findById(groupId);
		assertEquals(expected, actual);
	}

	@Test
	void test_findById_NoSuchId() {
		int groupId = -1;
		Optional<Group> group = Optional.empty();
		when(groupRepository.findById(groupId)).thenReturn(group);

		Group actual = groupService.findById(groupId);

		verify(groupRepository).findById(groupId);
		assertEquals(null, actual);
	}

	@Test
	void test_findByName() {
		String name = "Group1";
		Optional<Group> expected = Optional.of(new Group(1, "Group1"));

		when(groupRepository.findByName(name)).thenReturn(expected);

		Group actual = groupService.findByName(name);

		verify(groupRepository).findByName(name);
		assertEquals(expected.get(), actual);
	}

	@Test
	void test_findByName_NoSuchName() {
		String name = "Group01";
		Optional<Group> group = Optional.empty();

		when(groupRepository.findByName(name)).thenReturn(group);
		Group actual = groupService.findByName(name);

		verify(groupRepository).findByName(name);
		assertEquals(null, actual);
	}

	@Test
	void test_findAll() {
		List<Group> expected = List.of(new Group(1, "Group1"), new Group(2, "Group2"), new Group(3, "Group3"));

		when(groupRepository.findAll()).thenReturn(expected);

		List<Group> actual = groupService.findAll();

		verify(groupRepository).findAll();
		assertEquals(expected, actual);
	}

	@Test
	void test_findAll_EmptyDB() {
		List<Group> expected = new ArrayList<>();

		when(groupRepository.findAll()).thenReturn(new ArrayList<>());

		List<Group> actual = groupService.findAll();

		verify(groupRepository).findAll();
		assertEquals(expected, actual);
	}

	@Test
	void test_save() {
		Group expected = new Group(1, "Group1");
		Group group = new Group();
		group.setName("Group1");

		when(groupRepository.save(group)).thenReturn(expected);

		Group actual = groupService.save(group);

		verify(groupRepository).save(group);
		assertEquals(expected, actual);
	}

	@Test
	void test_deleteById() {
		int groupId = 3;

		groupService.deleteById(groupId);

		verify(groupRepository).deleteById(groupId);

	}

	@Test
	void test_findAllByStudentsCount() {
		int maxStudentCount = 1;
		List<Group> expected = List.of(new Group(1, "Group1"));

		when(groupRepository.findAllByStudentsCountLessOrEqual(maxStudentCount)).thenReturn(expected);

		List<Group> actual = groupService.findAllByStudentsCount(maxStudentCount);

		verify(groupRepository).findAllByStudentsCountLessOrEqual(maxStudentCount);
		assertEquals(expected, actual);
	}

}
