package com.ilgamumchu.demar.service;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.User;
import com.ilgamumchu.demar.dto.DiaryRequestDTO;
import com.ilgamumchu.demar.dto.DiaryResponseDTO;
import com.ilgamumchu.demar.repository.DiaryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiaryServiceImpl implements DiaryService{
    private final DiaryRepository diaryRepository;

    @Override
    @Transactional
    public Long save(DiaryRequestDTO diaryDTO){
        Diary diary = diaryRepository.save(diaryDTO.toEntity());
        return diary.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaryResponseDTO> findAllByUserId(User user) {
        return diaryRepository.findAllByUserId(user).stream()
                .map(DiaryResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DiaryResponseDTO findById(Long id) {
        return diaryRepository.findById(id).map(DiaryResponseDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일기입니다. id=" + id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        diaryRepository.deleteById(id);
    }
}