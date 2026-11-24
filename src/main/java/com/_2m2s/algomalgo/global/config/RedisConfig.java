package com._2m2s.algomalgo.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

/**
 * 캐시·임시 데이터용 RedisTemplate (Jackson 3, tools.jackson).
 * 역직렬화 허용 범위를 프로젝트 패키지(com._2m2s.algomalgo.)와 기본 값 타입으로 제한한다.
 *
 * 참고: 로그인 세션(Spring Session) 직렬화는 이 템플릿과 별개다. 기본값(JDK 직렬화)을 그대로 두었으며,
 * JSON 으로 바꾸려면 springSessionDefaultRedisSerializer 빈을 따로 만들어야 한다 (auth 담당, 12장).
 */
@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
				.allowIfSubType("com._2m2s.algomalgo.")
				.allowIfSubType("java.util.")
				.allowIfSubType("java.time.")
				.allowIfSubType("java.math.")
				.allowIfSubTypeIsArray()
				.build();

		GenericJacksonJsonRedisSerializer jsonSerializer = GenericJacksonJsonRedisSerializer.builder()
				.enableDefaultTyping(typeValidator)
				.build();

		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(jsonSerializer);
		template.setHashValueSerializer(jsonSerializer);
		template.afterPropertiesSet();
		return template;
	}
}
