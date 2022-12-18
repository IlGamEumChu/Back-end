package com.ilgamumchu.demar.dto;

import com.ilgamumchu.demar.domain.Music;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class RecommendRequestDTO {

    private String sentence;
    private List<Long> music_idx;

    @Builder
    public RecommendRequestDTO(String content, List<Long> music) {
        this.sentence = content;
        this.music_idx = music;
    }
}
