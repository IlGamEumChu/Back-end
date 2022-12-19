package com.ilgamumchu.demar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilgamumchu.demar.domain.*;
import com.ilgamumchu.demar.dto.*;
import com.ilgamumchu.demar.repository.DiaryRepository;

import com.ilgamumchu.demar.repository.MusicRepository;
import com.ilgamumchu.demar.repository.PlayListTrackRepository;
import com.ilgamumchu.demar.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiaryServiceImpl implements DiaryService{
    private final DiaryRepository diaryRepository;
    private final PlayListTrackRepository playListTrackRepository;
    private final RecommendRepository recommendRepository;
    private final MusicRepository musicRepository;

    private JSONObject parseToJson(String response) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        return jsonObject;
    }

    @Override
    @Transactional
    public Long save(DiaryRequestDTO diaryDTO) throws ParseException {
        String content = diaryDTO.getContent();

        List<Long> playList = playListTrackRepository.findAllByUserId(diaryDTO.getUserId())
                .stream().map(PlayListTrack::getMusicId).map(Music::getId)
                .collect(Collectors.toList());
        RecommendRequestDTO recommendRequestDTO = new RecommendRequestDTO(content, playList);

        WebClient client = WebClient.create();
        String response = client.post()
                .uri("http://18.233.49.59:8000/analysis")
                .bodyValue(recommendRequestDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONObject parsed = parseToJson(response);
        Diary diary = diaryRepository.save(diaryDTO.toEntity());

        JSONArray temp = (JSONArray) parsed.get("recommend");

        for(int i = 0; i < temp.size(); i++){
            Long num = (Long) temp.get(i);
            Music music = musicRepository.findById(num);
            RecommendResponseDTO recommendResponseDTO = new RecommendResponseDTO(diary,music);
            recommendRepository.save(recommendResponseDTO.toEntity());
        }
        return diary.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaryResponseDTO> findAllByUserId(User user) {
        return diaryRepository.findAllByUserIdOrderByCreatedAtDesc(user).stream()
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

    @Override
    @Transactional
    public List<MusicResponseDTO> findMusic(Long id){
        List<MusicResponseDTO> musicList = new ArrayList<>();
        Optional<Diary> diary = diaryRepository.findById(id);
        List<Recommend> recommendList = recommendRepository.findAllByDiaryId(diary);
        for(int i = 0; i < recommendList.size(); i++){
            Music music = recommendList.get(i).getMusicId();
            MusicResponseDTO musicResponseDTO = new MusicResponseDTO(music);
            musicList.add(musicResponseDTO);
        }
        return musicList;
    }
}