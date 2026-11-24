package com._2m2s.algomalgo.global.error;

import lombok.Getter;

/**
 * 업무 규칙 위반. 응답 message 는 항상 ErrorCode 의 메시지이므로 사용자 입력이나 내부 정보를 메시지에 넣지 않는다.
 * 타인 데이터에 접근한 경우에도 403이 아니라 <대상>_NOT_FOUND(404) 코드를 던진다.
 */
@Getter
public class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getCode());
		this.errorCode = errorCode;
	}
}
