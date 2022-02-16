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
	private Service service;

	@ManyToOne
	private User manager;

	@OneToOne
	private LeaveCounters leaveCounters;

	@OneToMany(mappedBy = "user")
	private List<Leave> leaves;

	public User() {
		super();
	}

	public User(String lastName, String firstName, String email, String password, Role role, Service service, User manager) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.service = service;
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
	
	public Service getService() {
		return service;
	}
	
	public void setService(Service service) {
		this.service = service;
	}
	
	public User getManager() {
		return manager;
	}
	
	public void setManager(User manager) {
		this.manager = manager;
	}
}
