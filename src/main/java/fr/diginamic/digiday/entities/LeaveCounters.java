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
	
	@OneToOne
	private User user;

	public LeaveCounters() {
		super();
	}

	public LeaveCounters(Integer remainingPaidLeaves, Integer paidLeavesTaken, Integer remainingRtt, Integer rttTaken, Integer unpaidLeavesTaken, User user) {
		super();
		this.remainingPaidLeaves = remainingPaidLeaves;
		this.paidLeavesTaken = paidLeavesTaken;
		this.remainingRtt = remainingRtt;
		this.rttTaken = rttTaken;
		this.unpaidLeavesTaken = unpaidLeavesTaken;
		this.user = user;
	}

	public void increaseRemainingPaidLeaves(Integer count) {
		remainingPaidLeaves += count;
	}

	public void increaseRemainingRtt(Integer count) {
		remainingRtt += count;
	}

	public void decreaseRemainingPaidLeaves(Integer count) {
		remainingPaidLeaves -= count;
	}

	public void decreaseRemainingRtt(Integer count) {
		remainingRtt -= count;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
