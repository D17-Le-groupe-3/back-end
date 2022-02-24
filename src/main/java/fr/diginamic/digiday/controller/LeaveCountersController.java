package fr.diginamic.digiday.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.digiday.dto.LeaveCountersDto;
import fr.diginamic.digiday.services.LeaveCountersService;

@RestController
@RequestMapping("leave-counters")
public class LeaveCountersController {
	
	private final LeaveCountersService leaveCountersService;
	private final ModelMapper modelMapper;
	
	public LeaveCountersController(LeaveCountersService leaveCountersService, ModelMapper modelMapper) {
		this.leaveCountersService = leaveCountersService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public ResponseEntity<?> getLeaveCounterByEmployee(@RequestParam Integer userId){
		return ResponseEntity.ok(leaveCountersService.getLeaveCountersByEmployee(userId).stream().map(leaveCounter -> modelMapper.map(leaveCounter, LeaveCountersDto.class)));
	}
}
