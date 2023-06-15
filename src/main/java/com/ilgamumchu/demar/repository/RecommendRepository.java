package com.ilgamumchu.demar.repository;

import com.ilgamumchu.demar.domain.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    List<Recommend> findAllByDiaryId(Long diaryId);
}
