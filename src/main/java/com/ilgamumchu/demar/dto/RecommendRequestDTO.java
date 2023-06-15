package com.ilgamumchu.demar.dto;

import java.util.List;

public record RecommendRequestDTO (String sentence, List<Long> musicList){
    public static RecommendRequestDTO of(String content, List<Long> musicList) {
        return new RecommendRequestDTO(content, musicList);
    }
}
