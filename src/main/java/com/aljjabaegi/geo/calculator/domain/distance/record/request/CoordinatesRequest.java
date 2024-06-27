package com.aljjabaegi.geo.calculator.domain.distance.record.request;

import com.aljjabaegi.geo.calculator.common.calculator.record.Coordinate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * 좌표 리스트 요청
 *
 * @author GEONLEE
 * @since 2024-06-26<br />
 * 20240-06-27 GEONLEE - 좌표 리스트 요청으로 명칭 변경<br />
 */
@Schema(description = "좌표 리스트 요청")
public record CoordinatesRequest(
        @Schema(description = "좌표 리스트")
        @Size(min = 2, max = 1000)
        @NotEmpty(message = "거리를 계산할 좌표 정보가 존재하지 않습니다.")
        List<Coordinate> coordinates
) {
}
