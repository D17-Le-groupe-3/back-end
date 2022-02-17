package fr.diginamic.digiday.controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

public class AuthController extends BaseController {

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }


}
