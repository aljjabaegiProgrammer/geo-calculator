package com.aljjabaegi.geo.calculator.domain.distance;

import com.aljjabaegi.geo.calculator.common.calculator.GeoCalculatorImpl;
import com.aljjabaegi.geo.calculator.common.exception.code.CommonErrorCode;
import com.aljjabaegi.geo.calculator.common.exception.custom.ServiceException;
import com.aljjabaegi.geo.calculator.common.response.ItemResponse;
import com.aljjabaegi.geo.calculator.common.response.ItemsResponse;
import com.aljjabaegi.geo.calculator.common.calculator.record.Coordinate;
import com.aljjabaegi.geo.calculator.domain.distance.record.request.CoordinatesRequest;
import com.aljjabaegi.geo.calculator.domain.distance.record.response.DistanceCalculationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-06-25
 */
@Service
@RequiredArgsConstructor
public class DistanceService {

    private final GeoCalculatorImpl geoCalculator;

    public ResponseEntity<ItemsResponse<DistanceCalculationResponse>> getDistanceBetweenPoints(CoordinatesRequest parameter) {
        List<DistanceCalculationResponse> distanceCalculationResponseList = geoCalculator.calculateDistanceBetweenCoordinates(parameter.coordinates());
        return ResponseEntity.ok()
                .body(ItemsResponse.<DistanceCalculationResponse>builder()
                        .status("AJ_OK")
                        .message("좌표간 거리(m)를 계산하였습니다.")
                        .totalSize((long) distanceCalculationResponseList.size())
                        .items(distanceCalculationResponseList)
                        .build());
    }

    public ResponseEntity<ItemResponse<Double>> getTotalDistance(CoordinatesRequest parameter) {
        if (ObjectUtils.isEmpty(parameter)) {
            throw new ServiceException(CommonErrorCode.INVALID_PARAMETER, "거리를 계산할 좌표 정보가 존재하지 않습니다.");
        }
        double totalDistance = 0.0;
        for (int i = 1, n = parameter.coordinates().size(); i < n; i++) {
            Coordinate startCoordinate = parameter.coordinates().get(i - 1);
            Coordinate endCoordinate = parameter.coordinates().get(i);
            totalDistance += geoCalculator.calculateDistanceBetweenCoordinate(startCoordinate, endCoordinate);
        }
        return ResponseEntity.ok()
                .body(ItemResponse.<Double>builder()
                        .status("AJ_OK")
                        .message("좌표간 총 거리(m)를 계산하였습니다.")
                        .item(totalDistance)
                        .build());
    }
}
