package com.aljjabaegi.geo.calculator.domain.summary.record.response;

import com.aljjabaegi.geo.calculator.common.calculator.record.Coordinate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * Bbox 좌표를 구한다. (좌표들의 최대, 최소 좌표)
 *
 * @author GEONLEE
 * @since 2024-06-27
 */
@Schema(description = "Bbox 좌표를 구한다. (좌표들의 최대, 최소 좌표)")
@Builder
public record BboxResponse(
        @Schema(description = "최대 좌표")
        Coordinate maxCoordinate,
        @Schema(description = "최소 좌표")
        Coordinate minCoordinate
) {
}
