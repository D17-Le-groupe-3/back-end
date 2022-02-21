package fr.diginamic.digiday.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.junit.Test;

import fr.diginamic.digiday.dto.CreateLeaveDto;
import fr.diginamic.digiday.entities.Leave;
import fr.diginamic.digiday.enums.LeaveType;
import fr.diginamic.digiday.enums.LeaveStatus;
import fr.diginamic.digiday.exceptions.DigidayWebApiException;

//@SpringBootTest
@DataJpaTest
@Import(LeaveService.class)
@Transactional
public class LeaveServiceTest {

	@Autowired
	private LeaveService leaveService;
	
	@Test
	public void createLeaveValidTest() {
		CreateLeaveDto data = new CreateLeaveDto(LocalDate.parse("2022-03-18"), LocalDate.parse("2022-04-04"), "", "RTT", 3);
		Leave leave = leaveService.createLeave(data);
		assertAll(
				() -> assertEquals(data.getStartDate(), leave.getStartDate()),
				() -> assertEquals(data.getEndDate(), leave.getEndDate()),
				() -> assertEquals(LeaveType.RTT, leave.getType()),
				() -> assertEquals(data.getReason(), leave.getReason()),
				() -> assertEquals(LeaveStatus.INITIAL, leave.getStatus())
		);
	}
	
	@Test (expected = DigidayWebApiException.class)
	public void createLeaveInvalidTest() {
		CreateLeaveDto data = new CreateLeaveDto(LocalDate.parse("2022-03-18"), LocalDate.parse("2022-04-04"), "", "RTTA", 3);
		Leave leave = leaveService.createLeave(data);
	}
}
