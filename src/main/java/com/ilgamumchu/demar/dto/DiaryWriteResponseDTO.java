package com.ilgamumchu.demar.dto;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.Music;

import java.util.List;

public record DiaryWriteResponseDTO(String title, String content, List<DiaryWriteMusicResponseDTO> recommendList) {
    public static DiaryWriteResponseDTO of(Diary diary, List<DiaryWriteMusicResponseDTO> recommendList) {
        return new DiaryWriteResponseDTO(diary.getTitle(), diary.getContent(), recommendList);
    }
}

