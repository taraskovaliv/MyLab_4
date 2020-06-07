package com.kovaliv.lab4.security.dtos;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class AddUserRequestDto {

    @Length(min = 4, max = 20)
    private String username;

    @Length(min = 6)
    private String password;

    @Length(min = 6)
    private String passwordConfirm;
}
