package com.aljjabaegi.geo.calculator.domain.distance.record.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * 링크 위 정류장 수선의 발 좌표 응답
 *
 * @author GEONLEE
 * @since 2024-03-05<br />
 * 2024-03-06 GEONLEE - record 로 변경<br />
 */
@Builder
@Schema(description = "링크 위 정류장 수선의 발 좌표 응답")
public record CoordinateOnLinkResponse(
        @Schema(description = "수선의 발 경도 좌표 \\/ NUMBER(14,8)", example = "129.0927188")
        double xcoordinate,
        @Schema(description = "수선의 발 위도 좌표 \\/ NUMBER(14,8)", example = "35.266139")
        double ycoordinate,
        @Schema(description = "링크 시작점 부터 수선의 발 까지 거리 \\/ NUMBER(4,1)", example = "1234.1")
        double distance) {
}
