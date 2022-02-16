package fr.diginamic.digiday.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Service extends BaseEntitie
{
	private String label;
	@ManyToOne
	private Company company;
	
	public String getLabel()
	{
		return label;
	}
	public void setLabel(String label)
	{
		this.label = label;
	}
	public Company getCompany()
	{
		return company;
	}
	public void setCompany(Company company)
	{
		this.company = company;
	}
}
