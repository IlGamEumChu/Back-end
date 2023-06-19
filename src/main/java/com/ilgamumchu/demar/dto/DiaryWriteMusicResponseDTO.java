package com.ilgamumchu.demar.dto;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.Music;

import java.util.List;

public record DiaryWriteMusicResponseDTO(String title, String artist, String cover) {
    public static DiaryWriteMusicResponseDTO of(Music music) {
        return new DiaryWriteMusicResponseDTO(music.getTitle(), music.getArtist(),music.getCover());
    }
}
