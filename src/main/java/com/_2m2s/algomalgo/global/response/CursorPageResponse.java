package com._2m2s.algomalgo.global.response;

import java.util.List;

/**
 * 목록 응답의 data 형식 (공통 API 명세서 초안 3.2, cursor 방식).
 * 결과가 없으면 items=[], 마지막 페이지면 nextCursor=null, hasNext=false.
 */
public record CursorPageResponse<T>(List<T> items, String nextCursor, boolean hasNext) {

	public static <T> CursorPageResponse<T> of(List<T> items, String nextCursor) {
		return new CursorPageResponse<>(items, nextCursor, nextCursor != null);
	}

	public static <T> CursorPageResponse<T> empty() {
		return new CursorPageResponse<>(List.of(), null, false);
	}
}
