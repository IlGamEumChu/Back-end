package com.ilgamumchu.demar.dto;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.Music;
import com.ilgamumchu.demar.domain.Recommend;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RecommendResponseDTO {
    private Diary diaryId;
    private Music musicId;

    public RecommendResponseDTO(Diary diary, Music music){
        this.diaryId = diary;
        this.musicId = music;
    }

    public Recommend toEntity(){
        return Recommend.builder()
                .diaryId(this.diaryId)
                .musicId(this.musicId)
                .build();
    }
}
