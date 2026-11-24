/**
 * analysis - AI 풀이 분석 (/api/analysis/**).
 * 담당 기능: FUNC-ANALYSIS-001~011, FUNC-ANALYSIS-013
 * 소유 테이블: analysis_jobs, analysis_attempts, solution_analyses, solution_analysis_concepts, solution_feedbacks
 * 외부 AI 동시 처리 최대 10건: @Async("analysisExecutor") (global.config.AsyncConfig)
 */
package com._2m2s.algomalgo.domain.analysis;
