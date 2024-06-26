package com.aljjabaegi.geo.calculator.domain.distance.record.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * 노선의 지점 거리 계산 응답
 *
 * @author GEONLEE
 * @since 2024-03-05<br />
 * 2024-03-06 GEONLEE - record 로 변경<br />
 * 2024-04-24 YS Lim - 버스, 링크, 노선 키 타입 변경 적용<br />
 * 2024-05-27 GEONLEE pointSequenceNumber 추가<br />
 */
@Schema(description = "지점 간 거리 계산 결과 응답")
@Builder
public record PointDistanceResponse(
        @Schema(description = "지점 시퀀스 넘버 / VARCHAR(10)", example = "1")
        Integer pointSequenceNumber,
        @Schema(description = "지점 구분 / VARCHAR(10)", example = "0")
        String pointDivision,
        @Schema(description = "지점 ID / VARCHAR(10)", example = "1920001703")
        String pointId,
        @Schema(description = "이전 지점 과의 거리 / NUMBER(4,1)", example = "100.1")
        double distanceFromBeforePoint,
        @Schema(description = "종점 까지 잔여 거리 / NUMBER(4,1)", example = "100.1")
        double remainingDistance) {
}
