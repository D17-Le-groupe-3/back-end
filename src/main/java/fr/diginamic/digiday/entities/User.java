package fr.diginamic.digiday.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import fr.diginamic.digiday.enums.Role;

@Entity
public class User extends BaseEntity {

	private String lastName;
	private String firstName;
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@ManyToOne
	private Department department;

	@ManyToOne
	private User manager;

	@OneToOne
	private LeaveCounters leaveCounters;

	@OneToMany(mappedBy = "user")
	private List<Leave> leaves;

	public User() {
		super();
	}

	public User(String lastName, String firstName, String email, String password, Role role, Department department, User manager) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.department = department;
		this.manager = manager;
	}

	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public LeaveCounters getLeaveCounters() {
		return leaveCounters;
	}

	public void setLeaveCounters(LeaveCounters leaveCounters) {
		this.leaveCounters = leaveCounters;
	}

	public List<Leave> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<Leave> leaves) {
		this.leaves = leaves;
	}

	public User getManager() {
		return manager;
	}
	
	public void setManager(User manager) {
		this.manager = manager;
	}
}
