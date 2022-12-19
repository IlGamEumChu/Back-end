package com.ilgamumchu.demar.domain;

import lombok.*;
import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Table(name="Recommend")
@Entity
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_id", nullable = false)
    private Diary diaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="music_id", nullable = false)
    private Music musicId;
}
