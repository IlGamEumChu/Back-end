package com.ilgamumchu.demar.service;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.dto.DiaryResponseDTO;
import com.ilgamumchu.demar.repository.DiaryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public List<DiaryResponseDTO> findAllByUserId(Long userId) {
        List<DiaryResponseDTO> diary = diaryRepository.findByUserId(userId);
        return diary;
    }
}
