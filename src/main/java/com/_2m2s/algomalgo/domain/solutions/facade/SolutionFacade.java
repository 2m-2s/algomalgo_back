package com._2m2s.algomalgo.domain.solutions.facade;

import com._2m2s.algomalgo.domain.solutions.dto.SolutionSummary;
import com._2m2s.algomalgo.domain.solutions.service.SolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 다른 도메인이 solutions 데이터를 쓰는 유일한 입구. */
@Component
@RequiredArgsConstructor
public class SolutionFacade {

    private final SolutionService solutionService;

    /** 본인 풀이만 돌려준다. 없거나 타인 것이면 SolutionService가 SOLUTION_NOT_FOUND를 던진다. */
    public SolutionSummary getOwnedSolution(Long userId, Long solutionId) {
        return solutionService.getOwnedSolutionSummary(userId, solutionId);
    }

    /** 회원 탈퇴 정리(FUNC-AUTH-009)에서 auth가 호출한다. */
    public void purgeUserData(Long userId) {
        solutionService.deleteAllByUser(userId);
    }
}
