package com.foxminded.SchoolSpringJdbc.model;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
	@SequenceGenerator(name = "student_seq", sequenceName = "student_seq", allocationSize = 1)
	@Column(name = "student_id")
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "group_id")
	private Group group;

	@ManyToMany(cascade = { CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinTable(name = "students_courses", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> courses;

	public Student(Integer id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(Group group, String firstName, String lastName) {
		this.group = group;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(Integer id, Group group, String firstName, String lastName) {
		this.id = id;
		this.group = group;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(Group group, String firstName, String lastName, List<Course> courses) {
		this.group = group;
		this.firstName = firstName;
		this.lastName = lastName;
		this.courses = courses;
	}

	public Student(Integer id, Group group, String firstName, String lastName, List<Course> courses) {
		this.id = id;
		this.group = group;
		this.firstName = firstName;
		this.lastName = lastName;
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", group=" + getGroupInfo() + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", courses=" + getCoursesInfo() + "]";
	}

	public String getGroupInfo() {
		if (this.group == null) {
			return "No group";
		}

		StringBuilder groupInfo = new StringBuilder("Group: ");
		groupInfo.append(group.getName());

		return groupInfo.toString();
	}

	public String getCoursesInfo() {
		if (this.courses == null || this.courses.isEmpty()) {
			return "No courses";
		}

		StringBuilder coursesInfo = new StringBuilder("Courses: ");
		for (Course course : this.courses) {
			coursesInfo.append(course.getName()).append(", ");
		}
		coursesInfo.setLength(coursesInfo.length() - 2);

		return coursesInfo.toString();
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
		Student student = (Student) o;
		return getId() != null && Objects.equals(getId(), student.getId());
	}

}
