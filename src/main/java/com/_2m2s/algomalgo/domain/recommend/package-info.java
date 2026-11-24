/**
 * recommend - 추천 문제 (/api/recommend/**).
 * 담당 기능: FUNC-RECOMMEND-003~008, FUNC-RECOMMEND-011
 * 소유 테이블: recommendation_daily_quotas, recommendation_batches, recommendation_candidates, user_recommendation_exclusions
 * 풀이는 SolutionFacade 로 읽기만 한다. 새 풀이 등록 시 후보 소비는 git 이 RecommendFacade 를 호출해 처리한다.
 */
package com._2m2s.algomalgo.domain.recommend;
