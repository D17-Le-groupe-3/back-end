package fr.diginamic.digiday.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Company extends BaseEntity {

	private Integer remainingRtt;
	private Integer rttTaken;

	@OneToMany(mappedBy = "company")
	private List<Department> departments;
	
	@OneToMany(mappedBy = "company")
	private List<CompanyHoliday> companyHolidays;

	public Company() {
		super();
	}

	public Company(Integer remainingRtt, Integer rttTaken) {
		super();
		this.remainingRtt = remainingRtt;
		this.rttTaken = rttTaken;
	}

	public void decreaseRemainingRtt(Integer count) {
		remainingRtt -= count;
	}

	public void increaseRttTaken(Integer count) {
		rttTaken += count;
	}

	public Integer getRemainingRtt() {
		return remainingRtt;
	}

	public void setRemainingRtt(Integer remainingRtt) {
		this.remainingRtt = remainingRtt;
	}

	public Integer getRttTaken() {
		return rttTaken;
	}

	public void setRttTaken(Integer rttTaken) {
		this.rttTaken = rttTaken;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> department) {
		this.departments = department;
	}

	public List<CompanyHoliday> getCompanyHolidays() {
		return companyHolidays;
	}

	public void setCompanyHolidays(List<CompanyHoliday> companyHolidays) {
		this.companyHolidays = companyHolidays;
	}
}
