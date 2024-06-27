package com.aljjabaegi.geo.calculator.domain.coordinateCalculation.record.response;

import com.aljjabaegi.geo.calculator.common.calculator.record.Coordinate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * 수선의 발 응답
 *
 * @author GEONLEE
 * @since 2024-06-26
 */
@Schema(description = "수선의 발 응답")
@Builder
public record FootOfPerpendicularResponse(
        @Schema(description = "수선의 발 좌표")
        Coordinate FootOfPerpendicularCoordinate,
        @Schema(description = "선의 시작 좌표부터 수선의 발 좌표까지의 거리")
        Double distance
) {
}
