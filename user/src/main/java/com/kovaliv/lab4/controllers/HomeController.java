package com.kovaliv.lab4.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> home(Principal principal) {
        return ResponseEntity.ok().body("You are logged in as " + principal.getName());
    }
}
