package com.ilgamumchu.demar.common;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {
    /** auth **/
    INVALID_MEMBER("존재하지 않는 회원입니다."),
    DUPLICATED_EMAIL("이미 사용중인 이메일입니다."),
    INVALID_EMAIL("유효하지 않은 이메일입니다."),
    INVALID_PASSWORD("유효하지 않은 비밀번호입니다."),

    /** Diary **/

    INVALID_DIARY("존재하지 않는 일기입니다."),
    PARSE_ERROR("모델 리턴값 파싱에 실패했습니다."),

    /** token **/
    EXPIRED_TOKEN("만료된 토큰입니다."),
    INVALID_SIGNATURE("유효하지 않은 서명입니다."),
    INVALID_AUTH_REQUEST("빈 토큰입니다.");

    private final String name;
}
