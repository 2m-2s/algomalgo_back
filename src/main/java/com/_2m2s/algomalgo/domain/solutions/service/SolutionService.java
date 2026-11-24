package com._2m2s.algomalgo.domain.solutions.service;

import com._2m2s.algomalgo.domain.solutions.dto.SolutionSummary;
import com._2m2s.algomalgo.domain.solutions.exception.SolutionErrorCode;
import com._2m2s.algomalgo.global.error.BusinessException;
import org.springframework.stereotype.Service;

/**
 * 풀이 서비스. 엔티티·리포지토리를 만들기 전의 최소 구현이다(SolutionFacade 가 컴파일되도록 둔 것).
 * 풀이 담당이 solutions 엔티티·리포지토리를 만들 때 아래 두 메서드를 실제 구현으로 교체한다.
 */
@Service
public class SolutionService {

	/** 본인 풀이만 돌려준다. 없거나 타인 것이면 SOLUTION_NOT_FOUND(404). 지금은 저장소가 없어 항상 없음으로 응답한다. */
	public SolutionSummary getOwnedSolutionSummary(Long userId, Long solutionId) {
		throw new BusinessException(SolutionErrorCode.SOLUTION_NOT_FOUND);
	}

	/** 회원 탈퇴 정리(FUNC-AUTH-009). 엔티티 생성 후 해당 사용자의 풀이를 삭제하도록 구현한다. */
	public void deleteAllByUser(Long userId) {
	}
}
