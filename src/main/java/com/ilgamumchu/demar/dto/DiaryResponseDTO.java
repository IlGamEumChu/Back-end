package com.ilgamumchu.demar.dto;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DiaryResponseDTO {
    private Long id;
    private User userId;
    private String title;
    private String content;
    private int emotion;
    private Date createdAt;

    public static DiaryResponseDTO create(Diary diary) {
        return new DiaryResponseDTO(diary.getId(), diary.getUserId(),
                diary.getTitle(), diary.getContent(), diary.getEmotion(),
                diary.getCreated_at());
    }

}
