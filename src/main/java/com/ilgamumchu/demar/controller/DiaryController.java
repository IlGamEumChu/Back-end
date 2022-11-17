package com.ilgamumchu.demar.controller;

import com.ilgamumchu.demar.dto.DiaryResponseDTO;
import com.ilgamumchu.demar.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "diary")
@RestController
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping(value = "/{userId}")
    public List<DiaryResponseDTO> getAllDiary(@PathVariable Long userId ){
        return diaryService.findAllByUserId(userId);
    }

}
