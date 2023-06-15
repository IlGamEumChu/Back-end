package com.ilgamumchu.demar.domain;

import lombok.*;
import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Entity
@Table(name = "\"Recommend\"")
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_id", nullable = false)
    private Diary diary;

    @ManyToOne
    @JoinColumn(name="music_id", nullable = false)
    private Music music;

    @Builder
    public Recommend(Diary diary, Music music) {
        this.diary = diary;
        this.music = music;
    }
}
