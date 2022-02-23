package fr.diginamic.digiday.dto;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * <p>
     * Conversion d'un objet Leave en objet ListModifConsultLeaveDto
     * </p>
     * 
     * @param leave : Demande de congé à traité
     * @return ListModifConsultLeaveDto : Demande de congé au format
     *         ListModifConsultLeaveDto
     * @since 1.0
     */
    static ListModifConsultLeaveDto toListModifyLeaveDto(Leave leave) {
	return new ListModifConsultLeaveDto(leave.getStartDate(), leave.getEndDate(), leave.getType().toString(), leave.getReason(),
		leave.getUser().getId(), leave.getStatus().toString());
    }

    /**
     * Conversion d'une liste de congés au format ListModifConsultLeaveDto
     * 
     * @param clients
     * @return liste de ListModifConsultLeaveDto
     */
    static List<ListModifConsultLeaveDto> toListModifyLeaveDto(List<Leave> clients) {
	List<ListModifConsultLeaveDto> clientsDto = new ArrayList<>();
	for (Leave c : clients) {
	    clientsDto.add(toListModifyLeaveDto(c));
	}
	return clientsDto;
    }
}
