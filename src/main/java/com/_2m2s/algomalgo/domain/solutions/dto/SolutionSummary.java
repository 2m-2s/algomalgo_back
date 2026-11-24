package com._2m2s.algomalgo.domain.solutions.dto;

import java.time.Instant;

public record SolutionSummary(Long solutionId, Long problemId, String language, Instant pushedAt) {
}
