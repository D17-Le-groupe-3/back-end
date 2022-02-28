package fr.diginamic.digiday.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

public class ModifyLeaveDto {
		
	 	@NotNull
	    @Future
	    private LocalDate startDate;

	    @NotNull
	    @Future
	    private LocalDate endDate;

	    @NotNull
	    private String type;

	    private String reason;
	    

	    public ModifyLeaveDto() {
	    }

	    public ModifyLeaveDto(LocalDate startDate, LocalDate endDate, String type, String reason, Integer id) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.reason = reason;
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

}
