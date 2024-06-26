package com.aljjabaegi.geo.calculator.domain.distance.record.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * 노선의 지점 거리 계산 조회 요청
 *
 * @author GEONLEE
 * @since 2024-03-05<br />
 * 2024-03-06 GEONLEE - record 로 변경<br />
 * 2024-04-24 YS Lim - 버스, 링크, 노선 키 타입 변경 적용<br />
 * 2024-05-27 GEONLEE - pointSequenceNumber 추가<br />
 */
@Schema(description = "노선의 지점 거리 계산 조회 요청")
public record PointDistanceRequest(
        @Schema(description = "지점 순번 / NUMBER", example = "1")
        Integer pointSequenceNumber,
        @Schema(description = "지점 구분 / VARCHAR(10)", example = "0")
        @NotNull
        String pointDivision,
        @Schema(description = "지점 ID / VARCHAR(10)", example = "1920001703")
        @NotNull
        String pointId) {
}
