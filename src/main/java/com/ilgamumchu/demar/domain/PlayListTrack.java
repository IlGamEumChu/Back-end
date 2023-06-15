package com.ilgamumchu.demar.domain;

import lombok.*;
import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Entity
@Table(name = "\"PlayListTrack\"")
public class PlayListTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_track_id")
    private Long playlist_track_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="music_id", nullable = false)
    private Music musicId;
}
