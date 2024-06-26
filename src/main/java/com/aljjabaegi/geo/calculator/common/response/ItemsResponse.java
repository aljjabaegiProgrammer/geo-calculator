package com.aljjabaegi.geo.calculator.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

/**
 * API 공통 복수 객체 응답 구조체, 기존 ResponseStructure 에서 단일/복수 응답 객체로 분리
 *
 * @author GEONLEE
 * @since 2024-03-19<br />
 * 2024-04-29 YS Lim - size -> totalSize로 변경<br />
 * 2024-06-20 GEONLEE - 응답 오류 개수를 리턴해주어야 하는 경우가 있어 errorSize 추가<br />
 */
@Builder
@Schema(description = "공통 복수 객체 응답 구조체")
public record ItemsResponse<T>(
        @Schema(description = "상태 코드", example = "NS_OK")
        String status,

        @Schema(description = "응답 메시지", example = "응답 메시지")
        String message,

        @Schema(description = "정상 응답 데이터 개수", example = "1")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long totalSize,

        @Schema(description = "오류 응답 데이터 개수", example = "1")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long errorSize,

        @Schema(description = "복수 응답 객체 / LIST")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<T> items) {
}
