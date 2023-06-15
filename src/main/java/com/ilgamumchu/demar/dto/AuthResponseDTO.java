package com.ilgamumchu.demar.dto;

public record AuthResponseDTO(String userName, String accessToken) {
    public static AuthResponseDTO of(String userName, String accessToken) {
        return new AuthResponseDTO(userName, accessToken);
    }
}
