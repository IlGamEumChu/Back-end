package com.ilgamumchu.demar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.ilgamumchu.demar.domain.Diary;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class DiaryResponseDTO {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private int emotion;
    private LocalDateTime createdAt;

    public static DiaryResponseDTO create(Diary diary) {
        return new DiaryResponseDTO(diary.getId(), diary.getUserId(),
                diary.getTitle(), diary.getContent(), diary.getEmotion(),
                diary.getCreatedAt());
    }

}
