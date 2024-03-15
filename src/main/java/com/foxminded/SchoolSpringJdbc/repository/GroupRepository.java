package com.foxminded.SchoolSpringJdbc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxminded.SchoolSpringJdbc.model.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {

	Optional<Group> findByName(String name);

	@Query("SELECT g FROM Group g WHERE SIZE(g.students) <= :maxStudentCount")
	List<Group> findAllByStudentsCountLessOrEqual(@Param("maxStudentCount") int maxStudentCount);

	@Override
	@Modifying
	default void deleteById(Integer id) {
		Optional<Group> groupOptional = findById(id);
		if (groupOptional.isPresent()) {
			groupOptional.get().getStudents().stream().forEach(s -> s.setGroup(null));
			delete(groupOptional.get());
		}
	}

}
