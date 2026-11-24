/**
 * git - GitHub 연동, 레포지토리 연결, Push 수집 (/api/git/**).
 * 담당 기능: FUNC-GIT-001~003, 005~009, FUNC-PUSH-001, 002, 004, 008, 009, 010
 * 소유 테이블: repository_connections, ingestion_jobs, ingestion_files
 * GitHub 웹훅·GitHub API 클라이언트는 담당자가 구현한다. 수집 후 problems -> solutions -> analysis/recommend/dashboard/common 순서로 facade 를 호출한다.
 */
package com._2m2s.algomalgo.domain.git;
