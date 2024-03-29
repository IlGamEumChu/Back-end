package com.ilgamumchu.demar.repository;

import com.ilgamumchu.demar.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long>{
    Optional<Diary> findById(Long id);
    List<Diary> findAllByUserIdOrderByCreatedAtDesc(Long id);
}
