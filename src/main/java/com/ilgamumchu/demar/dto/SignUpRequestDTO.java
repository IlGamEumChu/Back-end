package com.ilgamumchu.demar.dto;

import lombok.*;

@Data
@AllArgsConstructor
@ToString
public class SignUpRequestDTO {
    private String email;
    private String password;
    private String name;
    private String sp_id;
    private String sp_password;
}
