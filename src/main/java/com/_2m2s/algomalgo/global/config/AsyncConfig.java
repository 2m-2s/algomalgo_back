package com._2m2s.algomalgo.global.config;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 비동기·배치 기반.
 * - taskExecutor: @Async 기본 실행기 (Push 수집 후속 처리 등)
 * - analysisExecutor: 외부 AI 분석 전용. 동시 처리 최대 10건 (FUNC-ANALYSIS-013, AC-PUSH-008-03). 사용: @Async("analysisExecutor")
 * 스케줄러(@Scheduled)는 Boot 가 만든다 (spring.task.scheduling.pool.size).
 * 실행기를 직접 정의하면 Boot 기본 실행기가 빠지므로 기본 실행기도 여기서 만든다.
 * 대기열 방식(메모리 큐 vs DB 상태)·타임아웃·장애 복구는 명세에서 미확정(analysis.md 1243행)이므로 큐 크기는 시작값이다.
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig {

	@Bean(name = "taskExecutor")
	public ThreadPoolTaskExecutor taskExecutor(
			@Value("${app.async.core-pool-size}") int corePoolSize,
			@Value("${app.async.max-pool-size}") int maxPoolSize,
			@Value("${app.async.queue-capacity}") int queueCapacity) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("async-");
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(30);
		return executor;
	}

	@Bean(name = "analysisExecutor")
	public ThreadPoolTaskExecutor analysisExecutor(
			@Value("${app.async.analysis-concurrency}") int concurrency,
			@Value("${app.async.analysis-queue-capacity}") int queueCapacity) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(concurrency);
		executor.setMaxPoolSize(concurrency);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("analysis-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(30);
		return executor;
	}
}
