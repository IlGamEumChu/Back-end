package com.ilgamumchu.demar.repository;

import com.ilgamumchu.demar.domain.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, String> {
    boolean existsByTitle(String title);
    Music findByTitle(String title);
    Music findById(Long id);
}
