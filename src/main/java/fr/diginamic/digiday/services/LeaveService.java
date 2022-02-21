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
import fr.diginamic.digiday.exceptions.DigidayWebApiException;
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
	public Leave createLeave(CreateLeaveDto createLeaveDto) throws DigidayWebApiException {
		Leave leave = new Leave();
		leave.setStartDate(createLeaveDto.getStartDate());
		leave.setEndDate(createLeaveDto.getEndDate());
		checkDatesRules(leave);
		
		checkTypeRules(createLeaveDto);
		leave.setType(LeaveType.valueOf(createLeaveDto.getType()));

		leave.setReason(createLeaveDto.getReason());
		checkReasonRules(leave);
		
		User user = getUserById(createLeaveDto.getUserId());
		leave.setUser(user);
		leave.setStatus(LeaveStatus.INITIAL);
			
		return leaveRepo.save(leave);
	}
	
	private User getUserById(Integer id) throws DigidayNotFoundException
	{
		return userRepo.findById(id).orElseThrow(() -> new DigidayNotFoundException("User with ID " + id + " does not exist"));
	}

	private void checkDatesRules(Leave leave) throws DigidayBadRequestException {
		if (leave.getStartDate().compareTo(leave.getEndDate()) > 0)
			throw new DigidayBadRequestException("Start date is after end date");
	}
	
	private void checkTypeRules(CreateLeaveDto createLeaveDto) throws DigidayBadRequestException {
		try {
			LeaveType.valueOf(createLeaveDto.getType());
		}
		catch (Exception ex) {
			throw new DigidayBadRequestException("Leave type " + createLeaveDto.getType() + " does not match an existing one. Expected types are : "
													+ LeaveType.PAID_LEAVE.toString() + ", " + LeaveType.UNPAID_LEAVE.toString() + " or " + LeaveType.RTT.toString());
		}
	}
	
	private void checkReasonRules(Leave leave) {
		if (leave.getType().equals(LeaveType.UNPAID_LEAVE) && leave.getReason().isEmpty())
			throw new DigidayBadRequestException("Reason is required for leaves of type UNPAID_LEAVE");
	}
}
