package com.aljjabaegi.geo.calculator.domain.distance.record.response;

import com.aljjabaegi.geo.calculator.domain.distance.record.Coordinate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * 거리 계산 응답
 *
 * @author GEONLEE
 * @since 2024-06-21
 */
@Schema(description = "거리 계산 응답")
@Builder
public record DistanceCalculationResponse(

        @Schema(description = "시작 지점 좌표")
        Coordinate startCoordinate,
        @Schema(description = "종료 지점 좌표")
        Coordinate endCoordinate,
        @Schema(description = "거리(m)", example = "10.1")
        Double distance
) {
}
