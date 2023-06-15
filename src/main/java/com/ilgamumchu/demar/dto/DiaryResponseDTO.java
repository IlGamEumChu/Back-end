package com.ilgamumchu.demar.dto;

import com.ilgamumchu.demar.domain.Diary;

import java.util.Date;

public record DiaryResponseDTO(Long id, String title, String content, Date createdAt) {
        public static DiaryResponseDTO of(Diary diary) {
                return new DiaryResponseDTO(diary.getId(), diary.getTitle(), diary.getContent(), diary.getCreatedAt());
        }
}
