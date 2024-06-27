package com.aljjabaegi.geo.calculator.common.calculator.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * 좌표 정보
 *
 * @author GEONLEE
 * @since 2024-05-22
 */
@Builder
@Schema(description = "좌표")
public record Coordinate(
        @Schema(description = "경도, X", example = "126.75922354")
        Double longitude,
        @Schema(description = "위도, Y", example = "35.99849768")
        Double latitude
) {
}
