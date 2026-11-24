/**
 * dashboard - 대시보드 요약 정보 (/api/dashboard/**).
 * 담당 기능: FUNC-COMMON-008, FUNC-COMMON-011 (학습 상태 산출·저장)
 * 소유 테이블: learning_states, learning_state_concepts
 * 조합형: 대부분 다른 도메인 facade 의 집계. recommend 를 호출하지 않는다(recommend 가 이쪽을 호출한다).
 */
package com._2m2s.algomalgo.domain.dashboard;
