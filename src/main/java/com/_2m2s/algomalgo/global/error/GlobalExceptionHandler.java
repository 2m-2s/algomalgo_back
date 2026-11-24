package com._2m2s.algomalgo.global.error;

import com._2m2s.algomalgo.global.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 모든 예외를 ApiResponse(success=false, data=null)로 바꾼다.
 * 로그에는 예외 종류와 필드 이름만 남기고, 요청 값·예외 메시지(입력값이 섞일 수 있음)는 4xx에서 남기지 않는다.
 * (FUNC-COMMON-003: 민감한 코드·토큰·타인 정보를 오류에 노출하지 않는다)
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException e) {
		log.warn("[BUSINESS] code={}", e.getErrorCode().getCode());
		return toResponse(e.getErrorCode());
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class })
	public ResponseEntity<ApiResponse<Void>> handleBind(BindException e) {
		// 거부된 값(rejectedValue)은 남기지 않고 필드 이름만 남긴다
		List<String> fields = e.getBindingResult().getFieldErrors().stream().map(FieldError::getField).toList();
		log.warn("[INVALID_INPUT] fields={}", fields);
		return toResponse(GlobalErrorCode.INVALID_INPUT);
	}

	@ExceptionHandler({ ConstraintViolationException.class, HandlerMethodValidationException.class,
			MissingServletRequestParameterException.class, HttpMessageNotReadableException.class })
	public ResponseEntity<ApiResponse<Void>> handleInvalidInput(Exception e) {
		log.warn("[INVALID_INPUT] type={}", e.getClass().getSimpleName());
		return toResponse(GlobalErrorCode.INVALID_INPUT);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
		log.warn("[INVALID_INPUT] parameter={}", e.getName());
		// 날짜 파라미터(yyyy-MM-dd) 형식 오류는 INVALID_DATE_FORMAT
		if (LocalDate.class.equals(e.getRequiredType())) {
			return toResponse(GlobalErrorCode.INVALID_DATE_FORMAT);
		}
		return toResponse(GlobalErrorCode.INVALID_INPUT);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
		return toResponse(GlobalErrorCode.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleNoResource(NoResourceFoundException e) {
		return toResponse(GlobalErrorCode.RESOURCE_NOT_FOUND);
	}

	/** DB·Redis 연결 장애 등 내부 저장소 장애 (RedisConnectionFailureException 도 이 타입의 하위 클래스) */
	@ExceptionHandler(DataAccessResourceFailureException.class)
	public ResponseEntity<ApiResponse<Void>> handleStorageFailure(DataAccessResourceFailureException e) {
		log.error("[SERVICE_UNAVAILABLE] type={}", e.getClass().getName(), e);
		return toResponse(GlobalErrorCode.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
		// 위에서 다루지 않은 Spring MVC 4xx 예외(예: 지원하지 않는 Content-Type)는 500이 아니라 INVALID_INPUT 으로 보낸다
		if (e instanceof org.springframework.web.ErrorResponse er && er.getStatusCode().is4xxClientError()) {
			log.warn("[INVALID_INPUT] type={}", e.getClass().getSimpleName());
			return toResponse(GlobalErrorCode.INVALID_INPUT);
		}
		// 500은 원인 추적을 위해 스택트레이스를 남긴다. 예외 메시지에 입력값이 섞일 수 있으므로 운영 로그 접근을 제한한다(11장)
		log.error("[INTERNAL_SERVER_ERROR] type={}", e.getClass().getName(), e);
		return toResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ApiResponse<Void>> toResponse(ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getStatus()).body(ApiResponse.error(errorCode));
	}
}
