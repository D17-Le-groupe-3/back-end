package fr.diginamic.digiday.controller;

import fr.diginamic.digiday.dto.CreateLeaveDto;
import fr.diginamic.digiday.dto.DtoUtils;
import fr.diginamic.digiday.dto.LeaveDto;
import fr.diginamic.digiday.exceptions.DigidayWebApiException;
import fr.diginamic.digiday.services.LeaveService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("leaves")
public class LeaveController {

	private final LeaveService leaveService;
	private final ModelMapper modelMapper;
	
	public LeaveController(LeaveService leaveService, ModelMapper modelMapper) {
		this.leaveService = leaveService;
		this.modelMapper = modelMapper;
	}

	@GetMapping("to-validate")
	@ResponseBody
	public List<LeaveDto> getToValidateByDepartment(@RequestParam Integer departmentId) {
		return leaveService.getLeavesToValidateByDepartment(departmentId).stream()
				.map(leave -> modelMapper.map(leave, LeaveDto.class))
				.collect(Collectors.toList());
	}

	@PostMapping("validate")
	@ResponseBody
	public LeaveDto validateLeave(@RequestBody Map<String, Integer> leaveId) {
		return modelMapper.map(leaveService.validateLeave(leaveId.get("leaveId")), LeaveDto.class);
	}

	@PostMapping("reject")
	@ResponseBody
	public LeaveDto rejectLeave(@RequestBody Map<String, Integer> leaveId) {
		return modelMapper.map(leaveService.rejectLeave(leaveId.get("leaveId")), LeaveDto.class);
	}

	@PostMapping
	public ResponseEntity<?> createLeave(@RequestBody @Validated CreateLeaveDto createLeaveDto) {
		return ResponseEntity.ok(DtoUtils.toCreateLeaveDto(leaveService.createLeave(createLeaveDto)));
	}
	
	@ExceptionHandler(DigidayWebApiException.class)
	public ResponseEntity<Map<String, String>> onDigidayWebApiException(DigidayWebApiException ex) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
				.body(Map.of("error", ex.getMessage()));
	}
}
