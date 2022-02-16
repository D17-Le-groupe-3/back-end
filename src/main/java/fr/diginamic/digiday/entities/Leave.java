package fr.diginamic.digiday.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.diginamic.digiday.enums.LeaveStatus;
import fr.diginamic.digiday.enums.LeaveType;

@Entity
@Table(name = "leave_")
public class Leave extends BaseEntity {
	
	private LocalDate startDate;
	private LocalDate endDate;
	private String reason;
	
	@Enumerated(EnumType.STRING)
	private LeaveType type;
	
	@Enumerated(EnumType.STRING)
	private LeaveStatus status;

	@ManyToOne
	private User user;

	public Leave() {
		super();
	}

	public Leave(LocalDate startDate, LocalDate endDate, String reason,	LeaveType type, LeaveStatus status, User user) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.reason = reason;
		this.type = type;
		this.status = status;
		this.user = user;
	}

	public LocalDate getStartDate() {
		return startDate;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public LeaveType getType() {
		return type;
	}
	
	public void setType(LeaveType type) {
		this.type = type;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public LeaveStatus getStatus() {
		return status;
	}
	
	public void setStatus(LeaveStatus status) {
		this.status = status;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
