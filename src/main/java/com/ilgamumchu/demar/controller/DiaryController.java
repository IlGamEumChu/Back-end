package com.ilgamumchu.demar.controller;

import com.ilgamumchu.demar.common.ApiResponse;
import static com.ilgamumchu.demar.common.ResponseMessage.*;

import com.ilgamumchu.demar.dto.DiaryWriteRequestDTO;
import com.ilgamumchu.demar.service.DiaryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v2/diary")
public class DiaryController {
    private final DiaryService diaryService;

    @ApiOperation(value = "일기 작성")
    @PostMapping("/")
    public ResponseEntity<ApiResponse> createDiary(@ApiIgnore Principal principal, @RequestBody @Valid DiaryWriteRequestDTO request) {
        val response = diaryService.writeDiary(getMemberId(principal), request);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_DIARY_WRITE.getMessage(), response));
    }

    @ApiOperation(value = "일기 목록 가져오기")
    @GetMapping(value = "/")
    public ResponseEntity<ApiResponse> getDiaryList(@ApiIgnore Principal principal) {
       val response = diaryService.getDiaryList(getMemberId(principal));
       return ResponseEntity.ok(ApiResponse.success(SUCCESS_DIARY_LIST_GET.getMessage(), response));
    }

    @ApiOperation(value = "단일 일기 가져오기")
    @GetMapping(value = "/{diaryId}")
    public ResponseEntity<ApiResponse> getDiary(@PathVariable Long diaryId) {
        val response = diaryService.getDiary(diaryId);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_DIARY_GET.getMessage(), response));
    }

    @ApiOperation(value = "일기 삭제하기")
    @DeleteMapping(value = "/delete/{diaryId}")
    public ResponseEntity<ApiResponse> deleteDiary(@PathVariable Long diaryId){
        diaryService.deleteById(diaryId);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_DIARY_DELETE.getMessage()));
    }

    @ApiOperation(value = "일기 추천 음악 가져오기")
    @GetMapping(value = "/music/{diaryId}")
    public ResponseEntity<ApiResponse> getDiaryMusicList(@PathVariable Long diaryId){
        val response = diaryService.getDiaryMusicList(diaryId);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_MUSIC_LIST_GET.getMessage(), response));
    }

    private Long getMemberId(Principal principal) {
        return nonNull(principal) ? Long.valueOf(principal.getName()) : null;
    }
}
