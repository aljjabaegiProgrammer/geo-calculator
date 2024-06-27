package com.aljjabaegi.geo.calculator.domain.footOfPerpendicular;

import com.aljjabaegi.geo.calculator.common.calculator.GeoCalculatorImpl;
import com.aljjabaegi.geo.calculator.common.response.ItemResponse;
import com.aljjabaegi.geo.calculator.domain.footOfPerpendicular.record.FootOfPerpendicularRequest;
import com.aljjabaegi.geo.calculator.domain.footOfPerpendicular.record.FootOfPerpendicularResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author GEONLEE
 * @since 2024-06-25
 */
@Service
@RequiredArgsConstructor
public class FootOfPerpendicularService {

    private final GeoCalculatorImpl routeCalculator;

    public ResponseEntity<ItemResponse<FootOfPerpendicularResponse>> getFootOfPerpendicular(FootOfPerpendicularRequest parameter) {
        FootOfPerpendicularResponse footOfPerpendicularResponse = routeCalculator.getFootOfPerpendicular(parameter.coordinate(), parameter.lineCoordinates());
        return ResponseEntity.ok()
                .body(ItemResponse.<FootOfPerpendicularResponse>builder()
                        .status("AJ_OK")
                        .message("수선의 발 좌표와 길이(m)를 구했습니다.")
                        .item(footOfPerpendicularResponse)
                        .build());
    }
}
