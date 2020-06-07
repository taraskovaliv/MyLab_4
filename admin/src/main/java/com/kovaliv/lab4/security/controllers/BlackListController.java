package com.kovaliv.lab4.security.controllers;

import com.kovaliv.lab4.dtos.AddUserToBlackListDto;
import com.kovaliv.lab4.security.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class BlackListController {
    private final UserService userService;

    @PostMapping("/addToBlackList")
    public ResponseEntity<String> add(@RequestBody AddUserToBlackListDto addUserToBlackListDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addToBlackList(addUserToBlackListDto));
    }
}
