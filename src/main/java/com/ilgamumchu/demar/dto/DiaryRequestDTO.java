package com.ilgamumchu.demar.dto;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class DiaryRequestDTO {

    private Long id;
    private User userId;
    private String title;
    private String content;
    private int emotion;
    private Date createdAt;

    @Builder
    public DiaryRequestDTO(User userId, String title, String content, int emotion, Date createdAt) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.emotion = emotion;
        this.createdAt = createdAt;
    }

    public Diary toEntity(){
        return Diary.builder()
                .user_id(this.userId)
                .title(this.title)
                .content(this.content)
                .emotion(this.emotion)
                .created_at(this.createdAt)
                .build();
    }
}
