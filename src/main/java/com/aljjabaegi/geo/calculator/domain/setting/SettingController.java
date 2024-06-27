package com.aljjabaegi.geo.calculator.domain.setting;

import com.aljjabaegi.geo.calculator.common.response.ItemResponse;
import com.aljjabaegi.geo.calculator.domain.setting.record.request.SettingModifyRequest;
import com.aljjabaegi.geo.calculator.domain.setting.record.response.SettingModifyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GEONLEE
 * @since 2024-06-25
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "[GEO-000] 설정 정보", description = "[담당자 : GEONLEE]")
@RequestMapping("/v1/settings")
public class SettingController {

    private final SettingService settingService;

    @PostMapping(value = "/modify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "계산에 필요한 설정 값을 세팅한다.", description = """
            - distanceScale : 거리 표출 소수점 자리 (default 1)<br />
            - coordinateScale : 좌표 계산시 소수점 (default 8), 작게 설정할 경우 오차가 커지므로 주의!
            """,
            operationId = "GEO-000-01"
    )
    public ResponseEntity<ItemResponse<SettingModifyResponse>> modifySettings(@RequestBody @Valid SettingModifyRequest parameter) {
        return settingService.modifySettings(parameter);
    }

}
