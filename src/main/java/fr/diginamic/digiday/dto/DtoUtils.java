package fr.diginamic.digiday.dto;

import java.util.ArrayList;
import java.util.List;

import fr.diginamic.digiday.entities.Leave;

public interface DtoUtils {
	static CreateLeaveDto toCreateLeaveDto(Leave leave) {
        return new CreateLeaveDto(leave.getStartDate(), leave.getEndDate(), leave.getType().toString(), leave.getReason(), leave.getUser().getId());
    }

    static List<CreateLeaveDto> toCreateLeaveDto(List<Leave> clients) {
        List<CreateLeaveDto> clientsDto = new ArrayList<>();
        for (Leave c : clients) {
            clientsDto.add(toCreateLeaveDto(c));
        }
        return clientsDto;
    }
}
