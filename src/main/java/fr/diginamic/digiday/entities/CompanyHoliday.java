package fr.diginamic.digiday.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import fr.diginamic.digiday.enums.CompanyHolidayType;
import fr.diginamic.digiday.enums.LeaveStatus;

@Entity
public class CompanyHoliday extends BaseEntity {
	
	private LocalDate date;
	private String comment;
	
	@Enumerated(EnumType.STRING)
	private LeaveStatus status;
	
	@Enumerated(EnumType.STRING)
	private CompanyHolidayType type;

	@ManyToOne
	private Company company;
	
	public CompanyHoliday() {
		super();
	}

	public CompanyHoliday(LocalDate date, String comment, LeaveStatus status, CompanyHolidayType type, Company company) {
		super();
		this.date = date;
		this.comment = comment;
		this.status = status;
		this.type = type;
		this.company = company;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public CompanyHolidayType getType() {
		return type;
	}
	
	public void setType(CompanyHolidayType type) {
		this.type = type;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}

	public LeaveStatus getStatus() {
		return status;
	}

	public void setStatus(LeaveStatus status) {
		this.status = status;
	}
}
