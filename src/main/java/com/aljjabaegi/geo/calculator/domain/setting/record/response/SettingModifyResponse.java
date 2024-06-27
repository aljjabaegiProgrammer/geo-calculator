package com.aljjabaegi.geo.calculator.domain.setting.record.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * 설정 수정 응답
 *
 * @author GEONLEE
 * @since 2024-06-26
 */
@Schema(description = "설정 수정 응답")
@Builder
public record SettingModifyResponse(
        @Schema(description = "거리 응답 소수점 설정 값", example = "1")
        Integer distanceScale,
        @Schema(description = "좌표 계산 및 응답 시 좌표 소수점 설정 값", example = "8")
        Integer coordinateScale
) {
}
