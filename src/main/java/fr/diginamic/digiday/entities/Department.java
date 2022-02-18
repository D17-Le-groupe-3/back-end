package fr.diginamic.digiday.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Department extends BaseEntity {
	
	private String label;
	
	@ManyToOne
	private Company company;
	
	@OneToMany(mappedBy = "department")
	private List<User> users;

	public Department() {
		super();
	}
	
	public Department(String label, Company company) {
		super();
		this.label = label;
		this.company = company;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
