package com.ilgamumchu.demar.controller;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.Music;
import com.ilgamumchu.demar.domain.PlayListTrack;
import com.ilgamumchu.demar.domain.User;
import com.ilgamumchu.demar.dto.DiaryRequestDTO;
import com.ilgamumchu.demar.dto.DiaryResponseDTO;
import com.ilgamumchu.demar.dto.RecommendRequestDTO;
import com.ilgamumchu.demar.repository.PlayListTrackRepository;
import com.ilgamumchu.demar.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ilgamumchu.demar.security.jwt.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping(value = "diary")
@RestController
public class DiaryController {
    private final DiaryService diaryService;
    private final PlayListTrackRepository playListTrackRepository;

    private JSONObject parseToJson(String response) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        return jsonObject;
    }

    @PostMapping("/write")
    public @ResponseBody JSONObject diaryWrite(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid DiaryRequestDTO diaryDTO) throws Exception {
        //content, music_idx
        User user = userDetails.getUser();
        String content = diaryDTO.getContent();

        List<Long> playList = playListTrackRepository.findAllByUserId(user)
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

        diaryDTO.setUserId(user);
        diaryService.save(diaryDTO);
        return parsed;
    }

    @GetMapping(value = "/")
    public List<DiaryResponseDTO> getUserDiaryList(@AuthenticationPrincipal UserDetailsImpl userDetails){
       User user = userDetails.getUser();
       return diaryService.findAllByUserId(user);
    }

    @GetMapping(value = "/{diaryId}")
    public DiaryResponseDTO getSpecificDiary(@PathVariable Long diaryId){
        return diaryService.findById(diaryId);
    }

    @GetMapping(value = "/{diaryId}/delete")
    public String deleteDiary(@PathVariable Long diaryId){
        diaryService.deleteById(diaryId);
        return "deleted";
    }
}
