package com.ilgamumchu.demar.controller;

import com.ilgamumchu.demar.domain.Diary;
import com.ilgamumchu.demar.domain.User;
import com.ilgamumchu.demar.dto.DiaryRequestDTO;
import com.ilgamumchu.demar.dto.DiaryResponseDTO;
import com.ilgamumchu.demar.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ilgamumchu.demar.security.jwt.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "diary")
@RestController
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping(value = "/{diaryId}")
    public String getAllDiary(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long diaryId){
//        if (userDetails != null) {
//            User user = userDetails.getUser();
//            System.out.println(user.getRole());
//        }
        System.out.println(userDetails.getUser().getId());
        return "test";
    }

    @GetMapping("/write")
    public String diaryWritePage(){

        return "diary/diary_write";
    }

    @PostMapping("/write")
    public @ResponseBody String diaryWrite(@RequestBody @Valid DiaryRequestDTO diaryDTO) throws Exception {
        diaryService.save(diaryDTO);
        return "success";
    }

    @GetMapping(value = "/{userId}")
    public List<DiaryResponseDTO> getUserDiaryList(@PathVariable User userId){
       return diaryService.findAllByUserId(userId);
    }

    @GetMapping(value = "/{userId}/{diaryId}")
    public DiaryResponseDTO getSpecificDiary(@PathVariable Long diaryId){
        return diaryService.findById(diaryId);
    }

    @GetMapping(value = "/{userId}/{diaryId}/delete")
    public String deleteDiary(@PathVariable Long diaryId){
        diaryService.deleteById(diaryId);
        return "deleted";
    }
}
