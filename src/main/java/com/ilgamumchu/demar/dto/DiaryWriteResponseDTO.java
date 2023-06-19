package com.ilgamumchu.demar.dto;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.Music;

import java.util.List;

public record DiaryWriteResponseDTO(String title, String content, List<Music> recommendList) {
    public static DiaryWriteResponseDTO of(Diary diary, List<Music> recommendList) {
        return new DiaryWriteResponseDTO( diary.getTitle(), diary.getContent(), recommendList);
    }
}
