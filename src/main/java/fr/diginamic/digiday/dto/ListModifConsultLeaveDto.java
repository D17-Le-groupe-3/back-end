package fr.diginamic.digiday.dto;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * <p>
 * DTO permetant l'échange d'informations avec le front dans le cadre de
 * consultation ou modification de demande de congés
 * </p>
 * <p>Liste des attributs</p>
 * <ul>
 * <li>startDate : Date de début de congé</li>
 * <li>endDate : Date de fin de congé</li>
 * <li>type : Type de congé (PAID_LEAVE, UNPAID_LEAVE,RTT)</li>
 * <li>motif : Motif des congés obligatoire pour une demande de congés sans
 * solde. Facultatif dans les autres cas</li>
 * <li>userId : identifiant de l'utilisateur</li>
 * <li>status : Statut de la demande
 * (INITIAL,PENDING_VALIDATION,VALIDATED,REJECTED)</li>
 * </ul>
 * 
 * @author LOTT
 * @since 1.0
 */
public class ListModifConsultLeaveDto {

    @NotNull
    @Future
    private LocalDate startDate;

    @NotNull
    @Future
    private LocalDate endDate;

    @NotNull
    private String type;

    @NotNull
    private String status;

    private String reason;
    private Integer userId;

    /**
     * <p>
     * Constructeur sans paramètres
     * </p>
     */
    public ListModifConsultLeaveDto() {
    }

    /**
     * <p>
     * Constructeur
     * </p>
     * 
     * @param startDate : Date de début de congé
     * @param endDate   : Date de fin de congé
     * @param type      : Type de congé (PAID_LEAVE, UNPAID_LEAVE,RTT)
     * @param reason    : Motif des congés
     * @param userId    : identifiant de l'utilisateur
     * @param status    : Statut de la demande
     */
    public ListModifConsultLeaveDto(LocalDate startDate, LocalDate endDate, String type, String reason, Integer userId, String status) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.reason = reason;
		this.status = status;
		this.userId = userId;
    }

    // **** Getter et setter

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

    /**
     * @return the status
     */
    public String getStatus() {
    	return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
    	this.status = status;
    }
}
