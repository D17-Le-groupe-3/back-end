package fr.diginamic.digiday.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Company extends BaseEntity {

	private Integer remainingRtt;
	private Integer rttTaken;

	@OneToMany(mappedBy = "company")
	private List<Service> services;
	
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

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public List<CompanyHoliday> getCompanyHolidays() {
		return companyHolidays;
	}

	public void setCompanyHolidays(List<CompanyHoliday> companyHolidays) {
		this.companyHolidays = companyHolidays;
	}
}
