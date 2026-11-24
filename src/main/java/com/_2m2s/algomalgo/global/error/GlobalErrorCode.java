package com._2m2s.algomalgo.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/** 공통 에러 코드 (공통 API 명세서 초안 4.4 표). RESOURCE_NOT_FOUND, METHOD_NOT_ALLOWED 는 원안에서 유지한 것으로 초안 표에는 없다. */
@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

	INVALID_INPUT(HttpStatus.BAD_REQUEST, "요청 값이 올바르지 않습니다."),
	INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "날짜 형식이 올바르지 않습니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
	ACCOUNT_WITHDRAWN(HttpStatus.FORBIDDEN, "탈퇴 처리된 계정입니다."),
	CONCURRENT_SESSION(HttpStatus.CONFLICT, "다른 기기에서 사용 중입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
	SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "일시적으로 서비스를 이용할 수 없습니다. 잠시 후 다시 시도해주세요."),

	// 초안 표에 없음 (원안 유지, 팀 확인 필요)
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 대상을 찾을 수 없습니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않는 요청 방식입니다.");

	private final HttpStatus status;
	private final String message;
}
