package fr.diginamic.digiday.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class LeaveCounters extends BaseEntity {
	
	private Integer remainingPaidLeaves;
	private Integer paidLeavesTaken;
	private Integer remainingRtt;
	private Integer rttTaken;
	private Integer unpaidLeavesTaken;

	public LeaveCounters() {
		super();
	}

	public LeaveCounters(Integer remainingPaidLeaves, Integer paidLeavesTaken, Integer remainingRtt, Integer rttTaken, Integer unpaidLeavesTaken) {
		super();
		this.remainingPaidLeaves = remainingPaidLeaves;
		this.paidLeavesTaken = paidLeavesTaken;
		this.remainingRtt = remainingRtt;
		this.rttTaken = rttTaken;
		this.unpaidLeavesTaken = unpaidLeavesTaken;
	}
	
	public Integer getRemainingPaidLeaves() {
		return remainingPaidLeaves;
	}
	
	public void setRemainingPaidLeaves(Integer remainingPaidLeaves) {
		this.remainingPaidLeaves = remainingPaidLeaves;
	}
	
	public Integer getPaidLeavesTaken() {
		return paidLeavesTaken;
	}
	
	public void setPaidLeavesTaken(Integer paidLeavesTaken) {
		this.paidLeavesTaken = paidLeavesTaken;
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
	
	public Integer getUnpaidLeavesTaken() {
		return unpaidLeavesTaken;
	}
	
	public void setUnpaidLeavesTaken(Integer unpaidLeavesTaken) {
		this.unpaidLeavesTaken = unpaidLeavesTaken;
	}
}
