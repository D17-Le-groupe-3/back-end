package fr.diginamic.digiday.controller;

import fr.diginamic.digiday.dto.UserDto;
import fr.diginamic.digiday.entities.User;
import fr.diginamic.digiday.exceptions.DigidayNotFoundException;
import fr.diginamic.digiday.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserController(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("test")
    public Map<String,Object> home() {
        Map<String,Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }

    @PostMapping("/user")
    public UserDto user(@RequestBody Map<String, String> credentials) {
        User user = userRepository.findByEmailAndPassword(credentials.get("email"), credentials.get("password"))
                .orElseThrow(() -> new DigidayNotFoundException("User not found"));
        return modelMapper.map(user, UserDto.class);
    }

}
