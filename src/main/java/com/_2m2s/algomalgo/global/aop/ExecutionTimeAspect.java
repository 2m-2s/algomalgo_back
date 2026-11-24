package com._2m2s.algomalgo.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {

	private static final long SLOW_THRESHOLD_MS = 1000L;

	@Around("execution(* com._2m2s.algomalgo.domain..controller..*(..))")
	public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		try {
			return joinPoint.proceed();
		} finally {
			stopWatch.stop();
			long elapsed = stopWatch.getTotalTimeMillis();
			String target = joinPoint.getSignature().getDeclaringType().getSimpleName() + "."
					+ joinPoint.getSignature().getName() + "()";
			if (elapsed >= SLOW_THRESHOLD_MS) {
				log.warn("[SLOW] {} took {}ms", target, elapsed);
			} else {
				log.info("[TIME] {} took {}ms", target, elapsed);
			}
		}
	}
}
