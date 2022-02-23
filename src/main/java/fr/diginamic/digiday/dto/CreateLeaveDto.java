package fr.diginamic.digiday.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * DTO permetant l'échange d'informations avec le front dans le cadre de la
 * création d'une demande de congés. Reprends l'ensemble des informations de
 * l'entité sauf le statut qui est fixé par défault à INITIAL
 * </p>
 * <ul>
 * Liste des attibuts
 * </ul>
 * <li>startDate : Date de début de congé</li>
 * <li>endDate : Date de fin de congé</li>
 * <li>type : Type de congé (PAID_LEAVE, UNPAID_LEAVE,RTT)</li>
 * <li>motif : Motif des congés obligatoire pour une demande de congés sans
 * solde. Facultatif dans les autres cas</li>
 * <li>userId : identifiant de l'utilisateur</li>
 * 
 * @author LPOU & LOTT
 * @since 1.0
 */
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
