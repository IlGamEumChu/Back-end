package com.ilgamumchu.demar.dto;

import java.util.List;

public record RecommendRequestDTO (String diary, List<Long> music_idx){
    public static RecommendRequestDTO of(String diary, List<Long> music_idx) {
        return new RecommendRequestDTO(diary, music_idx);
    }
}
