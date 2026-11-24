package com._2m2s.algomalgo.global.error;

import org.springframework.http.HttpStatus;

/**
 * 에러 코드 공통 인터페이스. 각 도메인의 {Domain}ErrorCode enum 이 구현한다.
 * 응답의 code 는 enum 이름(예: INVALID_INPUT) 그대로다.
 */
public interface ErrorCode {

	/** enum 이 기본으로 제공하는 name() */
	String name();

	HttpStatus getStatus();

	String getMessage();

	default String getCode() {
		return name();
	}
}
