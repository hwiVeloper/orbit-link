package dev.hwiveloper.orbitlink.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum APIConst {
    SUCCESS(0, "성공"),
    INVALID_PARAMS(1, "유효하지 않은 요청입니다."),
    EXTERNAL_API_ERROR(9000, "외부 시스템 연동 오류"),
    FILE_SIZE_EXCEEDED(9001, "파일 크기가 너무 큽니다. 최대 10MB까지 업로드 가능합니다."),
    FILE_NOT_IMAGE(9002, "이미지 파일만 업로드 가능합니다."),
    SYSTEM_ERROR(9999, "시스템 오류입니다. 잠시 후 다시 시도해주세요."),

    // 인증, 사용자 관련,
    DUPLICATE_EMAIL(1000, "사용자 이메일이 중복되었습니다."),
    USER_NOT_FOUND(1001, "아이디 또는 비밀번호를 확인해 주세요."),
    EMAIL_NOT_FOUND(1002, "이메일을 확인해 주세요."),
    PASSWORD_INVALID(1003, "비밀번호 형식을 확인해 주세요."),
    PASSWORD_INCORRECT(1004, "비밀번호를 확인해 주세요."),
    PASSWORD_SAME(1005, "현재 비밀번호와 변경할 비밀번호가 동일합니다."),
    ;

    private final int resCode;
    private final String resMsg;
}
