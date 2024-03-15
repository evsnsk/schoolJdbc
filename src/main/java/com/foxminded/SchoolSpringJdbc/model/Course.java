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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
	@SequenceGenerator(name = "course_seq", sequenceName = "course_seq", allocationSize = 1)
	@Column(name = "course_id")
	private Integer id;

	@Column(name = "course_name")
	private String name;

	@Column(name = "course_description")
	private String description;

	@ManyToMany(cascade = { CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinTable(name = "students_courses", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	private List<Student> students;

	public Course(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Course(String name, String description, List<Student> students) {
		this.name = name;
		this.description = description;
		this.students = students;
	}

	public Course(Integer id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.students = new ArrayList<>();
	}

	public Course(Integer id, String name, String description, List<Student> students) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.students = students;
	}

	@Override
	public String toString() {
		return "Сourse [id=" + id + ", name=" + name + ", description=" + description + "]";
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
		Course course = (Course) o;
		return getId() != null && Objects.equals(getId(), course.getId());
	}

}
