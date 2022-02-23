package fr.diginamic.digiday.dto;

import fr.diginamic.digiday.entities.Leave;

/**
 * <p>
 * Classe utilitaire de conversion des classes DTO Leave
 * </p>
 * 
 * @author LOTT
 * @author LPOU
 * @since 1.0
 */
public interface DtoUtils {
    /**
     * <p>
     * Conversion d'un objet Leave en objet CreateLeaveDto
     * </p>
     * 
     * @param leave : Demande de congé à traité
     * @return CreateLeaveDto : Demande de congé au format CreateLeaveDto
     * @since 1.0
     */
    static CreateLeaveDto toCreateLeaveDto(Leave leave) {
	return new CreateLeaveDto(leave.getStartDate(), leave.getEndDate(), leave.getType().toString(), leave.getReason(), leave.getUser().getId());
    }

}
