package com.aljjabaegi.geo.calculator.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * API 공통 ERROR RESPONSE STRUCTURE (BUILDER PATTERN)
 *
 * @author GEONLEE
 * @apiNote
 * @since 2023-12-27<br />
 * 2024-03-06 GEONLEE - record 로 변경<br/>
 * 2024-03-29 GEONLEE - status -> String, 응답 코드로 변경<br />
 * 2024-05-21 GEONLEE - detail message 추가<br>
 */
@Builder
@Schema(description = "공통 에러 응답 구조체")
public record ErrorResponse(
        @Schema(description = "응답 코드", example = "NS_ERR_SVR_01")
        String status,
        @Schema(description = "응답 메시지", example = "요청하신 서비스에 문제가 있습니다.")
        String message,
        @Schema(description = "상세 메시지", example = "관리자에게 문의하세요!")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String detailMessage) {
}
