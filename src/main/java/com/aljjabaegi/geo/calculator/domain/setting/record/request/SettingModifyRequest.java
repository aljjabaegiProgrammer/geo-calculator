package com.aljjabaegi.geo.calculator.domain.setting.record.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * 설정 수정 요청
 *
 * @author GEONLEE
 * @since 2024-06-26
 */
@Schema(description = "설정 수정 요청")
public record SettingModifyRequest(
        @Schema(description = "거리 응답 소수점 설정 값", example = "1")
        @Min(0)
        @Max(10)
        Integer distanceScale,
        @Schema(description = "좌표 계산 및 응답 시 좌표 소수점 설정 값", example = "8")
        @Min(0)
        @Max(10)
        Integer coordinateScale
) {
}
