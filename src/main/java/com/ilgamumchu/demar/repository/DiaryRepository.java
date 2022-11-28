package com.ilgamumchu.demar.repository;

import com.ilgamumchu.demar.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long>{
//     Optional<Diary> findById(Long id);
}
