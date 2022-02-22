package fr.diginamic.digiday.services;

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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
	
	private User getUserById(Integer id) throws DigidayNotFoundException {
		return userRepo.findById(id).orElseThrow(() -> new DigidayNotFoundException("User with ID " + id + " does not exist"));
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
