package com.ilgamumchu.demar.repository;

import com.ilgamumchu.demar.domain.PlayListTrack;
import com.ilgamumchu.demar.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayListTrackRepository extends JpaRepository<PlayListTrack, String> {
    List<PlayListTrack> findAllByUserId(User user);
}
