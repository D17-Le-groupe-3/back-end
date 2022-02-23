package fr.diginamic.digiday.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.digiday.dto.CreateLeaveDto;
import fr.diginamic.digiday.dto.DtoUtils;
import fr.diginamic.digiday.dto.LeaveDto;
import fr.diginamic.digiday.exceptions.DigidayWebApiException;
import fr.diginamic.digiday.services.LeaveService;

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
	return leaveService.getLeavesToValidateByDepartment(departmentId).stream().map(leave -> modelMapper.map(leave, LeaveDto.class))
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
    
    @GetMapping(path = "/user/{id}")
    public ResponseEntity<?> getLeavesForUser(@RequestBody @Validated @PathVariable Integer id) {
    return ResponseEntity.ok(leaveService.getLeavesForUser(id).stream().map(leave -> modelMapper.map(leave, LeaveDto.class)));
    }

    @PostMapping
    public ResponseEntity<?> createLeave(@RequestBody @Validated CreateLeaveDto createLeaveDto) {
	return ResponseEntity.ok(DtoUtils.toCreateLeaveDto(leaveService.createLeave(createLeaveDto)));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteLeave(@PathVariable Integer id) {
	return ResponseEntity.ok(modelMapper.map(leaveService.deleteLeave(id), LeaveDto.class));
    }

    @ExceptionHandler(DigidayWebApiException.class)
    public ResponseEntity<Map<String, String>> onDigidayWebApiException(DigidayWebApiException ex) {
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(Map.of("error", ex.getMessage()));
    }
}
