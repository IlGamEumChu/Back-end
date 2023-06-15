package com.ilgamumchu.demar.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    /** auth **/
    SUCCESS_SIGN_UP("회원 가입 성공"),
    SUCCESS_LOGIN("로그인 성공"),

    /** diary **/
    SUCCESS_DIARY_WRITE("일기 작성 성공"),
    SUCCESS_DIARY_LIST_GET("일기 목록 불러오기 성공"),
    SUCCESS_DIARY_GET("일기 불러오기 성공"),
    SUCCESS_DIARY_DELETE("일기 삭제 성공"),
    SUCCESS_MUSIC_LIST_GET("음악 목록 불러오기 성공");

    private final String message;
}
