package com.foxminded.SchoolSpringJdbc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.proxy.HibernateProxy;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Groups")
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
	@SequenceGenerator(name = "group_seq", sequenceName = "group_seq", allocationSize = 1)
	@Column(name = "group_id")
	private Integer id;

	@Column(name = "group_name")
	private String name;

	@OneToMany(cascade = { CascadeType.ALL}, mappedBy = "group", fetch = FetchType.EAGER)
	private List<Student> students;

	public Group(String name) {
		this.name = name;
		this.students = new ArrayList<>();
	}

	public Group(Integer id, String name) {
		this.id = id;
		this.name = name;
		this.students = new ArrayList<>();
	}

	public Group(Integer id, String name, List<Student> students) {
		this.id = id;
		this.name = name;
		this.students = students;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", students=" + getStudentsInfo() + "]";
	}

	public String getStudentsInfo() {
		if (this.students == null || this.students.isEmpty()) {
			return "No students";
		}

		StringBuilder studentsInfo = new StringBuilder("Students: ");
		for (Student student : this.students) {
			studentsInfo.append(student.getFirstName()).append(" ").append(student.getLastName()).append(", ");
		}
		studentsInfo.setLength(studentsInfo.length() - 2);

		return studentsInfo.toString();
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
				: getClass().hashCode();
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy
				? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
				: o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
				: this.getClass();
		if (thisEffectiveClass != oEffectiveClass)
			return false;
		Group group = (Group) o;
		return getId() != null && Objects.equals(getId(), group.getId());
	}

}
