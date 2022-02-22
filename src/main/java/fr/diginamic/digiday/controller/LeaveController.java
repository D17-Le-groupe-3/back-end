package fr.diginamic.digiday.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.digiday.dto.CreateLeaveDto;
import fr.diginamic.digiday.dto.DtoUtils;
import fr.diginamic.digiday.exceptions.DigidayWebApiException;
import fr.diginamic.digiday.services.LeaveService;

@RestController
@RequestMapping("leaves")
public class LeaveController {

    private LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
	this.leaveService = leaveService;
    }

    @PostMapping
    public ResponseEntity<?> createLeave(@RequestBody @Validated CreateLeaveDto createLeaveDto) {
	return ResponseEntity.ok(DtoUtils.toCreateLeaveDto(leaveService.createLeave(createLeaveDto)));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteLeave(@PathVariable Integer id) {
	return ResponseEntity.ok(DtoUtils.toListModifyLeaveDto(leaveService.deleteLeave(id)));
    }

    @ExceptionHandler(DigidayWebApiException.class)
    public ResponseEntity<String> onDigidayWebApiException(DigidayWebApiException ex) {
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
