package com.aljjabaegi.geo.calculator.domain.summary;

import com.aljjabaegi.geo.calculator.common.calculator.GeoCalculatorImpl;
import com.aljjabaegi.geo.calculator.common.response.ItemResponse;
import com.aljjabaegi.geo.calculator.common.calculator.record.Coordinate;
import com.aljjabaegi.geo.calculator.domain.distance.record.request.CoordinatesRequest;
import com.aljjabaegi.geo.calculator.domain.summary.record.response.BboxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 좌표간 요약 정보 계산
 *
 * @author GEONLEE
 * @since 2024-06-25
 */
@Service
@RequiredArgsConstructor
public class SummaryService {

    private final GeoCalculatorImpl geoCalculator;

    public ResponseEntity<ItemResponse<Coordinate>> getCenterCoordinate(CoordinatesRequest parameter) {
        Coordinate centerCoordinate = geoCalculator.getCenterCoordinate(parameter.coordinates());
        return ResponseEntity.ok()
                .body(ItemResponse.<Coordinate>builder()
                        .status("AJ_OK")
                        .message("중심 좌표 계산에 성공하였습니다.")
                        .item(centerCoordinate)
                        .build());
    }

    public ResponseEntity<ItemResponse<BboxResponse>> getBboxCoordinate(CoordinatesRequest parameter) {
        BboxResponse bboxCoordinate = geoCalculator.getBboxCoordinate(parameter.coordinates());
        return ResponseEntity.ok()
                .body(ItemResponse.<BboxResponse>builder()
                        .status("AJ_OK")
                        .message("좌표들의 최대, 최소 좌표 계산에 성공하였습니다.")
                        .item(bboxCoordinate)
                        .build());
    }
}
