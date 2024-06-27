package com.aljjabaegi.geo.calculator.domain.coordinateCalculation.record;

import com.aljjabaegi.geo.calculator.domain.distance.record.Coordinate;
import com.aljjabaegi.geo.calculator.domain.distance.record.LineCoordinate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 수선의 발 요청
 *
 * @author GEONLEE
 * @since 2024-06-26
 */
@Schema(description = "수선의 발 요청")
public record FootOfPerpendicularRequest(
        @Schema(description = "순차적인 선의 좌표 리스트")
        List<LineCoordinate> lineCoordinates,

        @Schema(description = "점의 좌표")
        @NotNull
        Coordinate coordinate
) {
}
