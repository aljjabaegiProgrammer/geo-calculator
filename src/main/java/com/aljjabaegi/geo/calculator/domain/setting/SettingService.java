package com.aljjabaegi.geo.calculator.domain.setting;

import com.aljjabaegi.geo.calculator.common.calculator.GeoCalculatorImpl;
import com.aljjabaegi.geo.calculator.common.response.ItemResponse;
import com.aljjabaegi.geo.calculator.domain.setting.record.SettingModifyRequest;
import com.aljjabaegi.geo.calculator.domain.setting.record.SettingModifyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author GEONLEE
 * @since 2024-06-25
 */
@Service
@RequiredArgsConstructor
public class SettingService {

    private final GeoCalculatorImpl geoCalculator;

    public ResponseEntity<ItemResponse<SettingModifyResponse>> modifySettings(SettingModifyRequest parameter) {
        SettingModifyResponse settingModifyResponse = geoCalculator.modifySettings(parameter);
        return ResponseEntity.ok()
                .body(ItemResponse.<SettingModifyResponse>builder()
                        .status("AJ_OK")
                        .message("설정 정보를 수정하였습니다.")
                        .item(settingModifyResponse)
                        .build());
    }

}
