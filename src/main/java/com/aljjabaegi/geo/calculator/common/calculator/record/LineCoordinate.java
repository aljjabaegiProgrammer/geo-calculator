package com.aljjabaegi.geo.calculator.common.calculator.record;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 선의 좌표 정보
 *
 * @author GEONLEE
 * @since 2024-06-21
 */
@Schema(description = "선의 좌표 정보")
public record LineCoordinate(
        @Schema(description = "선의 좌표 순번", example = "1")
        Long pointSequence,
        @Schema(description = "경도, X", example = "126.75922354")
        Double longitude,
        @Schema(description = "위도, Y", example = "35.99849768")
        Double latitude
) {
}
