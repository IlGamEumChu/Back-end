package com.ilgamumchu.demar.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
public class LoginUserDTO {
    private String email;
    private String password;
}