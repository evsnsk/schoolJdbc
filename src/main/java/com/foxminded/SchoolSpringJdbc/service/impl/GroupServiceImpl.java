package com.foxminded.SchoolSpringJdbc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.SchoolSpringJdbc.model.Group;
import com.foxminded.SchoolSpringJdbc.repository.GroupRepository;
import com.foxminded.SchoolSpringJdbc.service.GroupService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	private final GroupRepository groupRepository;

	public GroupServiceImpl(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Group findById(int id) {
		log.info("Find Group by ID={}", id);
		Optional<Group> group = groupRepository.findById(id);
		if (group.isPresent()) {
			log.info("Group by ID={} is Present", id);
			return group.get();
		} else {
			log.warn("Group by ID={} doesn't exist", id);
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Group findByName(String name) {
		log.info("Find Group by NAME={}", name);
		Optional<Group> group = groupRepository.findByName(name);
		if (group.isPresent()) {
			log.info("Group by NAME={} is Present", name);
			return group.get();
		} else {
			log.warn("Group by NAME={} doesn't exist", name);
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Group> findAll() {
		log.info("Get all Groups");
		return groupRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Group> findAllByStudentsCount(int maxStudentCount) {
		return groupRepository.findAllByStudentsCountLessOrEqual(maxStudentCount);
	}

	@Override
	public Group save(Group group) {
		log.info("Save {}", group);
		return groupRepository.save(group);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		log.info("Delete Group by ID={}", id);
		groupRepository.deleteById(id);
	}

}
