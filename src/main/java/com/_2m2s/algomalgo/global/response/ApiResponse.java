package com._2m2s.algomalgo.global.response;

import com._2m2s.algomalgo.global.error.ErrorCode;

/**
 * 모든 API 응답의 공통 형식 (공통 API 명세서 초안 3장, 4.1).
 * 필드 4개는 항상 내려가며(null 포함) @JsonInclude 로 생략하지 않는다.
 * 성공: success=true, code=null, message=null, HTTP 200
 * 에러: success=false, code/message=공통 에러 코드 표의 값, data=null
 */
public record ApiResponse<T>(boolean success, String code, String message, T data) {

	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(true, null, null, data);
	}

	/** 돌려줄 데이터가 없는 성공(로그아웃, 읽음 처리 등): data=null */
	public static ApiResponse<Void> ok() {
		return new ApiResponse<>(true, null, null, null);
	}

	public static <T> ApiResponse<T> error(ErrorCode errorCode) {
		return new ApiResponse<>(false, errorCode.getCode(), errorCode.getMessage(), null);
	}
}
