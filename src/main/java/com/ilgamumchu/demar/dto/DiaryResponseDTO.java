package com.ilgamumchu.demar.dto;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@AllArgsConstructor
@ToString
public class DiaryResponseDTO {
    private Long id;
    private String title;
    private String content;
    private int emotion;
    private Date createdAt;

    public DiaryResponseDTO(Diary diary){
        this.id = diary.getId();
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.emotion = diary.getEmotion();
        this.createdAt = diary.getCreated_at();
    }
}
