package com.aljjabaegi.geo.calculator.domain.distance.record.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

/**
 * 노선 거리 계산 결과 응답
 *
 * @author GEONLEE
 * @since 2024-03-06<br />
 * 2024-03-06 GEONLEE - record 로 변경<br />
 */
@Schema(description = "노선 거리 계산 결과 응답")
@Builder
public record CalculatedDistanceResponse(
        @Schema(description = "노선 전체 링크 길이 / NUMBER(6,1)", example = "1985.6")
        double totalLinkLength,
        @Schema(description = "시작 정류장 의 링크 내 길이 / NUMBER(6,1)", example = "1985.6")

        double startBusStopLengthInLink,
        @Schema(description = "종점 정류장 의 링크 내 잔여 길이 / NUMBER(6,1)", example = "1985.6")
        double endBusStopRemainingLengthInLink,
        @Schema(description = "실제 노선 길이 (기점-종점) / NUMBER(6,1)", example = "1985.6")
        double actualRouteLength,
        @Schema(description = "지점간 거리 계산 응답 리스트 / LIST")
        List<PointDistanceResponse> pointsDistance) {
}
