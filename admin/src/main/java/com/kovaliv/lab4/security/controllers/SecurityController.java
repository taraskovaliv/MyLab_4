package com.kovaliv.lab4.security.controllers;

import com.kovaliv.lab4.security.dtos.AddUserRequestDto;
import com.kovaliv.lab4.security.dtos.LoginDto;
import com.kovaliv.lab4.security.dtos.UserDto;
import com.kovaliv.lab4.security.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class SecurityController {
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<UserDto> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.authenticateUser(loginDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody AddUserRequestDto addUserRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(addUserRequestDto));
    }
}