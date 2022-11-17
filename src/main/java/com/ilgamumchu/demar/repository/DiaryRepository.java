package com.ilgamumchu.demar.repository;

import com.ilgamumchu.demar.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ilgamumchu.demar.dto.DiaryResponseDTO;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long>{
    List<DiaryResponseDTO> findByUserId(Long id);
}
