/**
 * common - 알림·설정 (/api/common/**).
 * 담당 기능: FUNC-COMMON-003(오류 알림 저장), FUNC-COMMON-009
 * 소유 테이블: notifications, domain_events, domain_event_receipts (후자 둘은 10/31 회의에서 삭제 결정)
 * 가장 아래 도메인이므로 다른 도메인 facade 를 호출하지 않는다.
 */
package com._2m2s.algomalgo.domain.common;
