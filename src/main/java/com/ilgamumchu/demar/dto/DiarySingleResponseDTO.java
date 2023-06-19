package com.ilgamumchu.demar.dto;

import com.ilgamumchu.demar.domain.Diary;

import java.util.Date;
import java.util.List;

public record DiarySingleResponseDTO(Long id, String title, String content, Date createdAt, List<DiaryWriteMusicResponseDTO> recommendList) {
        public static DiarySingleResponseDTO of(Diary diary, List<DiaryWriteMusicResponseDTO> recommendList) {
                return new DiarySingleResponseDTO(diary.getId(), diary.getTitle(), diary.getContent(), diary.getCreatedAt(), recommendList);
        }
}
