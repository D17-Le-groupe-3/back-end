package fr.diginamic.digiday.dto;

/**
 * <p>
 * DTO permetant l'échange d'informations avec le front dans le cadre de
 * consultation du solde des compteurs de congés
 * </p>
 * <p>Liste des attributs</p>
 * <ul>
 * <li>remainingPaidLeaves: congés payés restant </li>
 * <li>paidLeavesTaken: congés payé déjà utilisés</li>
 * <li>remainingRtt: RTT restant</li>
 * <li>rttTaken: RTT déjà utilisés</li>
 * <li>unpaidLeavesTaken: congés sans soldes déjà utilisé</li>
 * <li>user: Dto du user </li>
 *   
 * </ul>
 * 
 * @author LOTT
 * @since 1.0
 */

public class LeaveCountersDto {
	private Integer remainingPaidLeaves;
	private Integer paidLeavesTaken;
	private Integer remainingRtt;
	private Integer rttTaken;
	private Integer unpaidLeavesTaken;
	private UserDto user;
	
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
	
	public UserDto getUser() {
		return user;
	}
	
	public void setUser(UserDto user) {
		this.user = user;
	}
}
