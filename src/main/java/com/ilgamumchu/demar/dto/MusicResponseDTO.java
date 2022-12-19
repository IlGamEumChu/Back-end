package com.ilgamumchu.demar.dto;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.Music;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MusicResponseDTO {
    private String title;
    private String artist;
    private String cover;
    private String url;

    public MusicResponseDTO(Music music){
        this.title = music.getTitle();
        this.artist = music.getArtist();
        this.cover = music.getCover();
        this.url = music.getUrl();
    }
}
