package fr.diginamic.digiday.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

public class CreateLeaveDto {
	
	@NotNull
	@Future
	private LocalDate startDate;
	
	@NotNull
	@Future
	private LocalDate endDate;
	
	@NotNull
	private String type;
	
	private String reason;
	private Integer userId;
	
	public CreateLeaveDto() {
	}

	public CreateLeaveDto(LocalDate startDate, LocalDate endDate, String type, String reason, Integer userId) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.reason = reason;
		this.userId = userId;
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}	
}
