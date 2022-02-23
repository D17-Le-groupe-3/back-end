package fr.diginamic.digiday.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import fr.diginamic.digiday.dto.CreateLeaveDto;
import fr.diginamic.digiday.entities.Leave;
import fr.diginamic.digiday.entities.LeaveCounters;
import fr.diginamic.digiday.entities.User;
import fr.diginamic.digiday.enums.LeaveStatus;
import fr.diginamic.digiday.enums.LeaveType;
import fr.diginamic.digiday.exceptions.DigidayBadRequestException;
import fr.diginamic.digiday.exceptions.DigidayNotFoundException;
import fr.diginamic.digiday.exceptions.DigidayWebApiException;
import fr.diginamic.digiday.repositories.LeaveCountersRepository;
import fr.diginamic.digiday.repositories.LeaveRepository;
import fr.diginamic.digiday.repositories.UserRepository;

/**
 * <p>
 * Classe service de gestion des demandes de congés
 * </p>
 * 
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
     * @throws DigidayNotFoundException   si le salarié n'existe pas en base
     * @throws DigidayBadRequestException si une règle de gestion n'est pas
     *                                    respectée
     * @author LPOU & LOTT
     * @since 1.0
     */

    @Transactional
    public Leave createLeave(CreateLeaveDto createLeaveDto) throws DigidayWebApiException {
	Leave leave = new Leave();
	// Gestion des dates
	leave.setStartDate(createLeaveDto.getStartDate());
	leave.setEndDate(createLeaveDto.getEndDate());
	checkRulesDateStartBeforeDateEnd(leave);
	// Gestion du type
	checkRulesMatchPrefefinedType(createLeaveDto);
	leave.setType(LeaveType.valueOf(createLeaveDto.getType()));
	// Gestion du motif
	leave.setReason(createLeaveDto.getReason());
	checkRulesReasonFilledForUnpaidLeave(leave);
	// Gestion du salarie
	User user = getEmployeeById(createLeaveDto.getUserId());
	leave.setUser(user);
	leave.setStatus(LeaveStatus.INITIAL);

	return leaveRepo.save(leave);

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
     * Vérifie que le salarié existe en base de donnée.
     * </p>
     * 
     * @param idEMployee : identifiant du salarie
     * @throws DigidayNotFoundException si le salarié n'existe pas en base
     * @author LPOU & LOTT
     * @see createLeave
     * @since 1.0
     */
    private User getEmployeeById(Integer idEMployee) throws DigidayNotFoundException {
	return userRepo.findById(idEMployee).orElseThrow(() -> new DigidayNotFoundException("User with ID " + idEMployee + " does not exist"));
    }

    /**
     * <p>
     * Vérifie que la date de début de congé est postérieur à la date du jour
     * </p>
     * 
     * @param Leave Demande de congé à vérifier
     * @throws DigidayBadRequestException si la date de début de la demande est
     *                                    antérieure à la date du jour
     * @author LOTT
     * @see deleteLeave
     * @since 1.0
     */
    private void checkRulesDateStartAfterCurrentDay(Leave leave) throws DigidayBadRequestException {
	if (leave.getStartDate().isBefore(LocalDate.now()))
	    throw new DigidayBadRequestException("Start date begin before current date - You are not allowed to delete this leave");
    }

    /**
     * <p>
     * Vérifie que la date de début de congé est antérieur à la date de fin de congé
     * </p>
     * 
     * @param Leave Demande de congé à vérifier
     * @throws DigidayBadRequestException si la date de début de la demande est
     *                                    antérieure à la date de fin
     * @author LPOU & LOTT
     * @see createLeave
     * @since 1.0
     */
    private void checkRulesDateStartBeforeDateEnd(Leave leave) throws DigidayBadRequestException {
	if (leave.getStartDate().compareTo(leave.getEndDate()) > 0)
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
     * @param CreateLeaveDto (DTO de congés) : Demande de congés à vérifier
     * @throws DigidayBadRequestException si le type de congé n'est pas conforme.
     * @author LPOU & LOTT
     * @see createLeave
     * @since 1.0
     */
    private void checkRulesMatchPrefefinedType(CreateLeaveDto createLeaveDto) throws DigidayBadRequestException {
	try {
	    LeaveType.valueOf(createLeaveDto.getType());
	} catch (Exception ex) {
	    throw new DigidayBadRequestException("Leave type " + createLeaveDto.getType() + " does not match an existing one. Expected types are : "
		    + LeaveType.PAID_LEAVE.toString() + ", " + LeaveType.UNPAID_LEAVE.toString() + " or " + LeaveType.RTT.toString());
	}
    }

    /**
     * <p>
     * Vérifie que le motif est renseigné pour une demande de congé de type Congé
     * sans solde
     * </p>
     * 
     * @param Leave Demande de congé à vérifier
     * @throws DigidayBadRequestException si la demande est une demande de congé
     *                                    sans solde et que le motif n'est pas
     *                                    renseigné.
     * @author LPOU & LOTT
     * @see createLeave
     * @since 1.0
     */
    private void checkRulesReasonFilledForUnpaidLeave(Leave leave) {
	if (leave.getType().equals(LeaveType.UNPAID_LEAVE) && leave.getReason().isEmpty())
	    throw new DigidayBadRequestException("Reason is required for leaves of type UNPAID_LEAVE");
    }

    /**
     * <p>
     * Vérifie que le status de la demande de congé est différente de "En attente de
     * validation" dans le cas d'une supression
     * </p>
     * 
     * @param Leave Demande de congé à vérifier
     * @throws DigidayBadRequestException si le status est égal à "En attente de
     *                                    validation"
     * @author LOTT
     * @see deleteLeave
     * @since 1.0
     */
    private void checkRulesStatusBeforeDelete(Leave leave) throws DigidayBadRequestException {
	if (leave.getStatus().equals(LeaveStatus.PENDING_VALIDATION)) {
	    throw new DigidayBadRequestException("You are not allowed to delete a leave whith the PENDING_VALIDATION status");
	}
    }

    public List<Leave> getLeavesToValidateByDepartment(Integer departmentId) {
	List<Leave> leaves = leaveRepo.findByUserDepartmentIdAndStatusIn(departmentId, List.of(LeaveStatus.PENDING_VALIDATION, LeaveStatus.REJECTED));
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
