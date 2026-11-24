/**
 * auth - 인증·계정 (/api/auth/**).
 * 담당 기능: FUNC-AUTH-001~010
 * 경로 예외: GET /api/me 는 첫 단어 규칙의 예외로 허용하며 컨트롤러는 domain/auth/controller 에 둔다.
 * 소유 테이블: users, account_deletion_jobs, github_credentials
 * GitHub OAuth 로그인은 담당자가 구현한다. 탈퇴 정리(FUNC-AUTH-009)는 각 도메인 facade 의 purgeUserData 를 호출한다.
 */
package com._2m2s.algomalgo.domain.auth;
