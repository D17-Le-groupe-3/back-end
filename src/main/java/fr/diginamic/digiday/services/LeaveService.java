package fr.diginamic.digiday.services;

import fr.diginamic.digiday.dto.CreateLeaveDto;
import fr.diginamic.digiday.dto.ModifyLeaveDto;
import fr.diginamic.digiday.entities.Leave;
import fr.diginamic.digiday.entities.LeaveCounters;
import fr.diginamic.digiday.entities.User;
import fr.diginamic.digiday.enums.LeaveStatus;
import fr.diginamic.digiday.enums.LeaveType;
import fr.diginamic.digiday.enums.Role;
import fr.diginamic.digiday.exceptions.DigidayBadRequestException;
import fr.diginamic.digiday.exceptions.DigidayNotFoundException;
import fr.diginamic.digiday.exceptions.DigidayWebApiException;
import fr.diginamic.digiday.repositories.LeaveCountersRepository;
import fr.diginamic.digiday.repositories.LeaveRepository;
import fr.diginamic.digiday.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Classe service de gestion des demandes de congés
 * </p>
 * 
 * @author KULR
 * @author LOTT
 * @author LPOU
 * @since 1.0
 */
@Service
public class LeaveService {

    private final LeaveRepository leaveRepo;
    private final UserRepository userRepo;
    private final LeaveCountersRepository leaveCountersRepo;

    public LeaveService(LeaveRepository leaveRepo, UserRepository userRepo, LeaveCountersRepository leaveCountersRepo) {
	this.leaveRepo = leaveRepo;
	this.userRepo = userRepo;
	this.leaveCountersRepo = leaveCountersRepo;
    }

    /**
     * <p>
     * Methode permetant de récupérer une demande de congé à partir de son
     * identifiant
     * </p>
     * 
     * @param idLeave : identifiant de la demande de congé
     * @return Optional Leave : Demande de congé si celle ci est trouvée
     * @throws DigidayNotFoundException si la demande de congé n'a pas été trouvée.
     * @author LOTT
     * @since 1.0
     */
    @Transactional
    public Leave searchLeaveById(Integer idLeave) throws DigidayWebApiException {
	return this.leaveRepo.findById(idLeave).orElseThrow(() -> new DigidayNotFoundException("Leave whith ID" + idLeave + " does not exist"));
    }

    /**
     * <p>
     * Crée une demande de congé après verification des règles de gestion
     * </p>
     * 
     * <ol>
     * <li>La date de fin doit être suppérieur ou égale à la date de début</li>
     * <li>Le types de congés doit correspondre à un des type prédéfini</li>
     * <li>Un commentaire est obligatoire pour une demande de congé sans solde</li>
     * <li>Le salarié indiqué doit être présent en base</li>
     * </ol>
     * 
     * @param createLeaveDto : informations de la demande de congé à créer.
     * @return La demande d'absence créée
     * @throws DigidayNotFoundException   si le salarié n'existe pas en base
     * @throws DigidayBadRequestException si une règle de gestion n'est pas
     *                                    respectée
     * @author LPOU
     * @author LOTT
     * @since 1.0
     */

    @Transactional
    public Leave createLeave(CreateLeaveDto createLeaveDto) throws DigidayWebApiException {

      Leave leave = new Leave();
      // Gestion des dates
      checkRulesDateStartBeforeDateEnd(createLeaveDto.getStartDate(), createLeaveDto.getEndDate());
      leave.setStartDate(createLeaveDto.getStartDate());
      leave.setEndDate(createLeaveDto.getEndDate());
      // Gestion du type
      checkRulesMatchPrefefinedType(createLeaveDto.getType());
      leave.setType(LeaveType.valueOf(createLeaveDto.getType()));
      // Gestion du motif
      checkRulesReasonFilledForUnpaidLeave(LeaveType.valueOf(createLeaveDto.getType()), createLeaveDto.getReason());
      leave.setReason(createLeaveDto.getReason());
      // Gestion du salarie
      User user = getEmployeeById(createLeaveDto.getUserId());
      leave.setUser(user);
      leave.setStatus(LeaveStatus.INITIAL);

      return leaveRepo.save(leave);
    }

    /**
     * <p>
     * Modifier une demande de congé après verification des règles de gestion
     * </p>
     * 
     * <ol>
     * <li>La date de fin doit être suppérieur ou égale à la date de début</li>
     * <li>Le types de congés doit correspondre à un des type prédéfini</li>
     * <li>Vérifie le statut (initiale et rejeté)</li>
     * <li>Un commentaire est obligatoire pour une demande de congé sans solde</li>
     * <li>Le salarié indiqué doit être présent en base</li>
     * </ol>
     * 
     * @param leaveModifyDto : informations de la demande de congé à modifier.
	 * @return La demande d'absence modifiée
     * @throws DigidayNotFoundException  si l'absence n'existe pas en base
     * @author ACLA
	 * @author LOTT
     * @since 1.0
     */
    
    @Transactional
    public Leave modifyLeave(Integer idLeave,ModifyLeaveDto leaveModifyDto) { // leaveModifyDto
    	// 1. vérification des règles de gestion pour déterminer si la demande de congé est modifiable
    	// recherche de la demande en base
    	Leave leaveToModify = this.leaveRepo.findById(idLeave)
    			.orElseThrow(() -> new DigidayNotFoundException("Leave whith ID" + idLeave + " does not exist"));
    	checkRulesAllowedTypeToModify(leaveToModify);
    	checkRulesDateStartAfterCurrentDay(leaveToModify);
    	// 2. Vérification des données reçues
    	checkRulesDateStartBeforeDateEnd(leaveModifyDto.getStartDate(), leaveModifyDto.getEndDate());
    	checkRulesMatchPrefefinedType(leaveModifyDto.getType());
    	checkRulesReasonFilledForUnpaidLeave(LeaveType.valueOf(leaveModifyDto.getType()), leaveModifyDto.getReason());
    	// Valorisation des données
    	leaveToModify.setStartDate(leaveModifyDto.getStartDate());
    	leaveToModify.setEndDate(leaveModifyDto.getEndDate());
    	leaveToModify.setType(LeaveType.valueOf(leaveModifyDto.getType()));
    	leaveToModify.setReason(leaveModifyDto.getReason());
		leaveToModify.setStatus(LeaveStatus.INITIAL);
    	return leaveRepo.save(leaveToModify);
    }

    

    /**
     * <p>
     * Supprime une demande de congé après verification des règles de gestion
     * </p>
     * 
     * <ol>
     * <li>Vérification de l'existance de la demande de congé</li>
     * <li>Une demande de congé ne peut être supprimée si la date de début est
     * antérieur à la date du jour</li>
     * <li>Une demande ne peut pas être supprimée avec le statut "En attente de
     * validation"</li>
     * </ol>
     * 
     * @param idLeaveToDelete : identifiant d'une demande de congé.
     * @return l'absence supprimée
     * @throws DigidayNotFoundException   si le salarié n'existe pas en base
     * @throws DigidayBadRequestException si une règle de gestion n'est pas
     *                                    respectée
     * @author LOTT
     * @since 1.0
     */

    @Transactional
    public Leave deleteLeave(Integer idLeaveToDelete) throws DigidayNotFoundException {
      Leave leaveToDelete;
      leaveToDelete = this.leaveRepo.findById(idLeaveToDelete)
        .orElseThrow(() -> new DigidayNotFoundException("Leave whith ID" + idLeaveToDelete + " does not exist"));
      checkRulesDateStartAfterCurrentDay(leaveToDelete);
      checkRulesStatusBeforeDelete(leaveToDelete);
      leaveRepo.delete(leaveToDelete);
      return leaveToDelete;
    }

    /**
     * <p>
     * Liste les demandes de congés d'un salarié
     * </p>
     * 
     * @param idEmployee : identifiant du salarié
     * @return une liste d'absences (potentiellement vide)
     * @throws DigidayNotFoundException si le salarié n'existe pas en base
     * @author KULR
     * @since 1.0
     */
    public List<Leave> getLeavesForUser(Integer idEmployee) {

		 User employee = this.getEmployeeById(idEmployee);
		    return leaveRepo.findByUser(employee);
	   }

    /**
     * <p>
     * Liste les demandes de congés d'un salarié pour un mois et une année donnés
     * </p>
     * 
     * @param idEmployee : identifiant du salarié
     * @param month      : mois
     * @param year       : année
     * @return une liste d'absences (potentiellement vide)
     * @throws DigidayNotFoundException si le salarié n'existe pas en base
     * @author KULR
     * @since 1.0
     */
    public List<Leave> getLeavesForUserByMonthAndYear(Integer idEmployee, Integer month, Integer year) {
      User employee = this.getEmployeeById(idEmployee);
      LocalDate monthStart = LocalDate.parse(String.format("%d-%02d-01", year, month));
      LocalDate monthEnd = monthStart.with(TemporalAdjusters.lastDayOfMonth());
      List<Leave> listLeaves = leaveRepo.findByUserAndTimeInterval(employee, monthStart, monthEnd);
      return listLeaves;
    }

    /**
     * <p>
     * Vérifie que le salarié existe en base de donnée.
     * </p>
     * 
     * @param idEmployee : identifiant du salarie
     * @return un employé s'il est trouvé
     * @throws DigidayNotFoundException si le salarié n'existe pas en base
     * @author LPOU
     * @author LOTT
     * @see #createLeave
     * @since 1.0
     */
    private User getEmployeeById(Integer idEmployee) throws DigidayNotFoundException {
	    return userRepo.findById(idEmployee).orElseThrow(() -> new DigidayNotFoundException("User with ID " + idEmployee + " does not exist"));
    }

    /**
     * <p>
     * Vérifie que la date de début de congé est postérieur à la date du jour
     * </p>
     * 
     * @param leave Demande de congé à vérifier
     * @throws DigidayBadRequestException si la date de début de la demande est
     *                                    antérieure à la date du jour
     * @author LOTT
     * @see #deleteLeave
     * @since 1.0
     */
    private void checkRulesDateStartAfterCurrentDay(Leave leave) throws DigidayBadRequestException {
      if (leave.getStartDate().isBefore(LocalDate.now()))
          throw new DigidayBadRequestException("Start date begin before current date - You are not allowed to delete or modify this leave");
    }

    /**
     * <p>
     * Vérifie que la date de début de congé est antérieur à la date de fin de congé
     * </p>
     * 
     * @param startDate Date de début de demande de congé à vérifier
     * @param endDate Date de fin de demande de congé à vérifier
     * @throws DigidayBadRequestException si la date de début de la demande est
     *                                    antérieure à la date de fin
     * @author LPOU
     * @author LOTT
     * @see #createLeave
     * @since 1.0
     */

    private void checkRulesDateStartBeforeDateEnd(LocalDate startDate, LocalDate endDate) throws DigidayBadRequestException {
      if (startDate.compareTo(endDate) > 0)
          throw new DigidayBadRequestException("Start date is after end date");
    }

    /**
     * <p>
     * Vérifie que le type de congé renseigné lors d'une création correspond aux
     * type de congés prédéfinis soit :
     * </p>
     * <ul>
     * <li>PAID_LEAVE - Congés payés</li>
     * <li>UNPAID_LEAVE - Congés sans solde</li>
     * <li>RTT - Réduction du temps de travail</li>
     * </ul>
     * 
     * @param typeToControl : Type de la demande de congés à vérifier
     * @throws DigidayBadRequestException si le type de congé n'est pas conforme.
     * @author LPOU
     * @author LOTT
     * @see #createLeave
     * @since 1.0
     */

    private void checkRulesMatchPrefefinedType(String typeToControl) throws DigidayBadRequestException {
        try {
            LeaveType.valueOf(typeToControl);
        } catch (Exception ex) {
            throw new DigidayBadRequestException("Leave type " + typeToControl + " does not match an existing one. Expected types are : "
              + LeaveType.PAID_LEAVE.toString() + ", " + LeaveType.UNPAID_LEAVE.toString() + " or " + LeaveType.RTT.toString());
        }
    }

    /**
     * <p>
     * Vérifie que le motif est renseigné pour une demande de congé de type Congé
     * sans solde
     * </p>
     * 
     * @param leaveType Type de la demande de congé à vérifier
     * @param reason motif de la demande de congé à vérifier
     * @throws DigidayBadRequestException si la demande est une demande de congé
     *                                    sans solde et que le motif n'est pas
     *                                    renseigné.
     * @author LPOU
     * @author LOTT
     * @see #createLeave
     * @since 1.0
     */

    private void checkRulesReasonFilledForUnpaidLeave(LeaveType leaveType, String reason) {
      if (leaveType.equals(LeaveType.UNPAID_LEAVE) && reason.isEmpty())
          throw new DigidayBadRequestException("Reason is required for leaves of type UNPAID_LEAVE");
    }

    /**
     * <p>
     * Vérifie que le status de la demande de congé est différente de "En attente de
     * validation" dans le cas d'une supression
     * </p>
     * 
     * @param leave Demande de congé à vérifier
     * @throws DigidayBadRequestException si le status est égal à "En attente de
     *                                    validation"
     * @author LOTT
     * @see #deleteLeave
     * @since 1.0
     */
    private void checkRulesStatusBeforeDelete(Leave leave) throws DigidayBadRequestException {
	if (leave.getStatus().equals(LeaveStatus.PENDING_VALIDATION)) {
	    throw new DigidayBadRequestException("You are not allowed to delete a leave whith the PENDING_VALIDATION status");
	}
    }
    
    /**
     * <p>
     * Vérifie que le status de la demande de congé n'a pas le statut en attente de validation ou validé.
     * (Pré-requis pour une modification)
     * </p>
     * 
     * @param leaveToModify
     * Demande de congé à vérifier
     * @throws DigidayBadRequestException si le statut en attente de validation ou validé
     * @author ACLA
     * @author LOTT
     * @see #modifyLeave
     * @since 1.0
     * 
     */
	private void checkRulesAllowedTypeToModify(Leave leaveToModify) {
		if(leaveToModify.getStatus().equals(LeaveStatus.PENDING_VALIDATION) ||  leaveToModify.getStatus().equals(LeaveStatus.VALIDATED)) {
    		throw new DigidayBadRequestException(" You're not allowed to modify or delete a leave with the status pending validation or validated");
    	}
	}

    public List<Leave> getLeavesToValidateByDepartment(Integer departmentId) {
	List<Leave> leaves = leaveRepo.findByUserDepartmentIdAndUserRoleNotAndStatusIn(departmentId, Role.MANAGER, List.of(LeaveStatus.PENDING_VALIDATION, LeaveStatus.REJECTED));
	if (leaves.isEmpty())
	    throw new DigidayNotFoundException("No leaves found");
	return leaves;
    }

    private Leave findLeaveAndSetStatus(Integer leaveId, LeaveStatus status) {
	Optional<Leave> optionalLeave = leaveRepo.findByIdAndStatus(leaveId, LeaveStatus.PENDING_VALIDATION);
	if (optionalLeave.isEmpty())
	    throw new DigidayNotFoundException("No leave with the id " + leaveId + "and pending validation was found");

	Leave leave = optionalLeave.get();
	leave.setStatus(status);
	return leave;
    }

    public Leave validateLeave(Integer leaveId) {
	Leave leave = findLeaveAndSetStatus(leaveId, LeaveStatus.VALIDATED);
	return leaveRepo.save(leave);
    }

    public Leave rejectLeave(Integer leaveId) {
	Leave leave = findLeaveAndSetStatus(leaveId, LeaveStatus.REJECTED);

	LeaveCounters leaveCounters = leave.getUser().getLeaveCounters();
	if (leave.getType() == LeaveType.PAID_LEAVE)
	    leaveCounters.increaseRemainingPaidLeaves(leave.getDuration());
	if (leave.getType() == LeaveType.RTT)
	    leaveCounters.increaseRemainingRtt(leave.getDuration());
	leaveCountersRepo.save(leaveCounters);

	return leaveRepo.save(leave);
    }

}
