package com.ilgamumchu.demar.service;

import com.ilgamumchu.demar.common.ErrorMessage;
import com.ilgamumchu.demar.domain.*;
import com.ilgamumchu.demar.dto.*;
import com.ilgamumchu.demar.repository.*;

import com.ilgamumchu.demar.utils.exception.AuthException;
import com.ilgamumchu.demar.utils.exception.DiaryException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final RecommendRepository recommendRepository;
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;

    @Value("${model.server.uri}")
    private String modelServerUri;

    @Transactional
    public DiaryWriteResponseDTO writeDiary(Long userId, DiaryWriteRequestDTO diaryDTO) {

        val user = userRepository.findUserById(userId)
                .orElseThrow(() -> new AuthException(ErrorMessage.INVALID_MEMBER.getName()));

        val playList = user.getPlayListTracks()
                .stream().map(PlayListTrack::getMusicId).map(Music::getId)
                .collect(Collectors.toList());

        val recommendRequestDTO = RecommendRequestDTO.of(diaryDTO.content(), playList);

        val client = WebClient.create();
        val response = client.post()
                .uri(modelServerUri + "/analyze")
                .bodyValue(recommendRequestDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONObject parsed;
        try {
            parsed = parseToJson(response);
        } catch (ParseException e) {
            throw new DiaryException(ErrorMessage.PARSE_ERROR.getName());
        }
        val diary = diaryRepository.save(Diary.builder()
                        .title(diaryDTO.title())
                        .content(diaryDTO.content())
                        .user(user)
                        .build());

        val recommedList = (JSONArray) parsed.get("recommend");

        val MusicList = (List<DiaryWriteMusicResponseDTO>) recommedList.stream()
                .map(num -> musicRepository.findById((Long) num))
                .map(music -> recommendRepository.save(Recommend.builder()
                        .music((Music) music)
                        .diary(diary)
                        .build()))
                .map(music -> DiaryWriteMusicResponseDTO.of((Music)music))
                .collect(Collectors.toList());

        return DiaryWriteResponseDTO.of(diary, MusicList);
    }
    public List<DiaryResponseDTO> getDiaryList(Long userId) {
        return diaryRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(DiaryResponseDTO::of)
                .collect(Collectors.toList());
    }

    public DiaryResponseDTO getDiary(Long id) {
        val diary = diaryRepository.findById(id)
                .orElseThrow(() -> new DiaryException(ErrorMessage.INVALID_DIARY.getName()));
        return DiaryResponseDTO.of(diary);
    }

    @Transactional
    public void deleteById(Long id) {
        diaryRepository.deleteById(id);
    }

    public List<Music> getDiaryMusicList(Long id){
        val recommendList = recommendRepository.findAllByDiaryId(id);

        return recommendList.stream()
                .map(recommend -> recommend.getMusic())
                .collect(Collectors.toList());
    }

    private JSONObject parseToJson(String response) throws ParseException {
        val jsonParser = new JSONParser();
        val jsonObject = (JSONObject) jsonParser.parse(response);
        return jsonObject;
    }
}