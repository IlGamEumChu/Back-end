package com.ilgamumchu.demar.dto;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class DiaryRequestDTO {

    private Long id;
    private User userId;
    private String title;
    private String content;

    @Builder
    public DiaryRequestDTO(User userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Diary toEntity(){
        return Diary.builder()
                .userId(this.userId)
                .title(this.title)
                .content(this.content)
                .build();
    }
}
