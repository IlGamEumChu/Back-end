package com.ilgamumchu.demar.service;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.User;
import com.ilgamumchu.demar.dto.DiaryRequestDTO;
import com.ilgamumchu.demar.dto.DiaryResponseDTO;
import com.ilgamumchu.demar.dto.MusicResponseDTO;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


import java.util.List;

public interface DiaryService {

    JSONObject save(DiaryRequestDTO diaryDTO) throws ParseException;

    List<DiaryResponseDTO> findAllByUserId(User user);

    DiaryResponseDTO findById(Long id);

    void deleteById(Long id);

    List<MusicResponseDTO> findMusic(Long id);
}