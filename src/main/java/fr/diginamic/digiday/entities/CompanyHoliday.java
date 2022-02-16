package fr.diginamic.digiday.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import fr.diginamic.digiday.enums.CompanyHolidayType;

@Entity
public class CompanyHoliday extends BaseEntitie {
	
	private LocalDate date;
	
	@Enumerated(EnumType.STRING)
	private CompanyHolidayType type;
	private String comment;

	@ManyToOne
	private Company company;

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
}
