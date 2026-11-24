package com._2m2s.algomalgo.domain.solutions.exception;

import com._2m2s.algomalgo.global.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SolutionErrorCode implements ErrorCode {
    // 타인의 풀이에 접근해도 403이 아니라 404 (공통 API 명세서 초안 4.2, 5장)
    SOLUTION_NOT_FOUND(HttpStatus.NOT_FOUND, "풀이를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
