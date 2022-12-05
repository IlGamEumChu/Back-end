package com.ilgamumchu.demar.dto;

import com.ilgamumchu.demar.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String name;
    private String email;
    private String password;

    public static UserResponseDTO create(User user) {
        return new UserResponseDTO(user.getName(),
                user.getEmail(), user.getPassword());
    }
}
