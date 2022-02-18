package fr.diginamic.digiday.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.digiday.dto.CreateLeaveDto;
import fr.diginamic.digiday.entities.Leave;
import fr.diginamic.digiday.exceptions.DigidayWebApiException;
import fr.diginamic.digiday.services.LeaveService;

@RestController
@RequestMapping("leaves")
public class LeavesController {

	private LeaveService leaveService;
	
	public LeavesController(LeaveService leaveService) {
		this.leaveService = leaveService;
	}
	
	@PostMapping
	public ResponseEntity<?> createLeave(@RequestBody @Validated CreateLeaveDto createLeaveDto) {
		return ResponseEntity.ok(leaveService.createLeave(createLeaveDto));
	}
	
	@ExceptionHandler(DigidayWebApiException.class)
	public ResponseEntity<String> onHotelWebApiException(DigidayWebApiException ex)
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
