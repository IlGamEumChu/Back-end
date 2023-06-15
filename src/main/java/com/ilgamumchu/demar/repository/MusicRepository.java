package com.ilgamumchu.demar.repository;

import com.ilgamumchu.demar.domain.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, String> {
    Music findById(Long id);
}
