package fr.diginamic.digiday.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import fr.diginamic.digiday.dto.CreateLeaveDto;
import fr.diginamic.digiday.entities.Leave;
import fr.diginamic.digiday.entities.User;
import fr.diginamic.digiday.enums.LeaveStatus;
import fr.diginamic.digiday.enums.LeaveType;
import fr.diginamic.digiday.exceptions.DigidayBadRequestException;
import fr.diginamic.digiday.exceptions.DigidayNotFoundException;
import fr.diginamic.digiday.repositories.LeaveRepository;
import fr.diginamic.digiday.repositories.UserRepository;

@Service
public class LeaveService {

	private LeaveRepository leaveRepo;
	private UserRepository userRepo;

	public LeaveService(LeaveRepository leaveRepo, UserRepository userRepo) {
		this.leaveRepo = leaveRepo;
		this.userRepo = userRepo;
	}

	@Transactional
	public Leave createLeave(CreateLeaveDto createLeaveDto) {
		Leave leave = new Leave();
		leave.setStartDate(createLeaveDto.getStartDate());
		leave.setEndDate(createLeaveDto.getEndDate());
		if (leave.getStartDate().compareTo(leave.getEndDate()) > 0)
			throw new DigidayBadRequestException("Start date is after end date");
		
		try {
			leave.setType(LeaveType.valueOf(createLeaveDto.getType()));
		}
		catch (Exception ex) {
			throw new DigidayBadRequestException("Leave type " + createLeaveDto.getType() + " does not match an existing one. Expected types are :" + LeaveType.values());
		}
		leave.setReason(createLeaveDto.getReason());
		if (leave.getType().equals(LeaveType.UNPAID_LEAVE) && leave.getReason().isEmpty())
			throw new DigidayBadRequestException("Reason is required for leaves of type UNPAID_LEAVE");
		
		User user = userRepo.findById(createLeaveDto.getUserId()).orElseThrow(() -> new DigidayNotFoundException("User with ID " + createLeaveDto.getUserId() + " does not exist"));
		leave.setUser(user);
		leave.setStatus(LeaveStatus.INITIAL);
			
		return leaveRepo.save(leave);
	}
}
