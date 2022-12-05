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
    public List<DiaryResponseDTO> findAllByUser(User user) {
        return diaryRepository.findAllByUser(user).stream()
                .map(DiaryResponseDTO::create)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Diary findById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long boardId) {

    }
}