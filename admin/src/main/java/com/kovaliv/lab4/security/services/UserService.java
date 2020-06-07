package com.kovaliv.lab4.security.services;


import com.kovaliv.lab4.dtos.AddUserToBlackListDto;
import com.kovaliv.lab4.security.dtos.AddUserRequestDto;
import com.kovaliv.lab4.security.dtos.LoginDto;
import com.kovaliv.lab4.security.dtos.UserDto;
import com.kovaliv.lab4.security.entities.User;

public interface UserService {
    UserDto save(AddUserRequestDto addUserRequestDto);

    User findByUsername(String username);

    UserDto authenticateUser(LoginDto loginDto);

    String addToBlackList(AddUserToBlackListDto addUserToBlackListDto);
}
