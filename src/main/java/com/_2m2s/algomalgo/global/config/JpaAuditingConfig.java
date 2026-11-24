package com._2m2s.algomalgo.global.config;

import java.time.Instant;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class JpaAuditingConfig {

	/** Instant(UTC) 로 시각을 기록한다 */
	@Bean
	public DateTimeProvider auditingDateTimeProvider() {
		return () -> Optional.of(Instant.now());
	}
}
