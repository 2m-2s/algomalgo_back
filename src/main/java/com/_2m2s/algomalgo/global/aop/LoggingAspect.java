package com._2m2s.algomalgo.global.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	@Pointcut("execution(* com._2m2s.algomalgo.domain..controller..*(..))")
	public void controllerMethods() {
	}

	@Before("controllerMethods()")
	public void logRequest(JoinPoint joinPoint) {
		String method = "-";
		String path = "-";
		if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) {
			HttpServletRequest request = attributes.getRequest();
			method = request.getMethod();
			path = request.getRequestURI(); // 쿼리스트링은 남기지 않는다
		}
		log.info("[API REQ] {} {} -> {}.{}()", method, path,
				joinPoint.getSignature().getDeclaringType().getSimpleName(),
				joinPoint.getSignature().getName());
	}

	@AfterReturning(pointcut = "controllerMethods()", returning = "result")
	public void logResponse(JoinPoint joinPoint, Object result) {
		log.info("[API RES] {}.{}() -> {}",
				joinPoint.getSignature().getDeclaringType().getSimpleName(),
				joinPoint.getSignature().getName(),
				result == null ? "void" : result.getClass().getSimpleName()); // 반환 값 내용은 남기지 않는다
	}
}
