package com.ilgamumchu.demar.repository;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend,Long> {
    List<Recommend> findAllByDiaryId(Optional<Diary> diary);
}
