package com.foxminded.SchoolSpringJdbc.service;

import java.util.List;

import com.foxminded.SchoolSpringJdbc.model.Group;

public interface GroupService {
	Group findById(int id);

	Group findByName(String name);

	List<Group> findAll();

	List<Group> findAllByStudentsCount(int maxStudentCount);

	Group save(Group group);

	void deleteById(int id);
}
