package com.aljjabaegi.geo.calculator.domain.distance;

import com.aljjabaegi.geo.calculator.common.response.ItemResponse;
import com.aljjabaegi.geo.calculator.common.response.ItemsResponse;
import com.aljjabaegi.geo.calculator.domain.distance.record.request.CoordinatesRequest;
import com.aljjabaegi.geo.calculator.domain.distance.record.response.DistanceCalculationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
@Tag(name = "[GEO-001] 거리 계산", description = "[담당자 : GEONLEE]")
@RequestMapping("/v1/distance")
public class DistanceController {

    private final DistanceService calculatorService;

    @PostMapping(value = "/between-coordinates", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "좌표간 직선 거리를 계산하여 리턴한다.", description = """
            Haversine 공식 으로 두 지점 사이의 대원 거리를 계산 한다. (지구 곡률 고려)<br />
            평면 좌표계인 유클리드 거리 공식 보다 장거리 계산에 정확함.<br />
            이전좌표 - 다음좌표 간 거리를 계산하여 리턴.<br />
            두개 이상의 좌표 정보 필수.<br />
            소수점 첫째짜리까지 표출 (HALF_UP)
            """,
            operationId = "GEO-001-01"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = {
                    @ExampleObject(name = "Sample",
                            value = """
                                    {
                                        "coordinates": [
                                            {
                                              "longitude": 126.75922354,
                                              "latitude": 35.99849768
                                            },
                                            {
                                              "longitude": 126.7596667,
                                              "latitude": 35.99863333
                                            },
                                            {
                                              "longitude": 126.7596238,
                                              "latitude": 35.99880713
                                            }
                                        ]
                                    }
                                    """
                    )}))
    public ResponseEntity<ItemsResponse<DistanceCalculationResponse>> getDistanceBetweenPoints(@RequestBody @Valid CoordinatesRequest parameter) {
        return calculatorService.getDistanceBetweenPoints(parameter);
    }

    @PostMapping(value = "/total-distance", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "좌표들의 총 거리(m)를 계산하여 리턴한다.", description = """
            소수점 첫째짜리까지 표출 (HALF_UP)
            """,
            operationId = "GEO-001-02"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = {
                    @ExampleObject(name = "Sample",
                            value = """
                                    {
                                        "coordinates": [
                                            {
                                              "longitude": 126.75922354,
                                              "latitude": 35.99849768
                                            },
                                            {
                                              "longitude": 126.7596667,
                                              "latitude": 35.99863333
                                            },
                                            {
                                              "longitude": 126.7596238,
                                              "latitude": 35.99880713
                                            }
                                        ]
                                    }
                                    """
                    )}))
    public ResponseEntity<ItemResponse<Double>> getTotalDistance(@RequestBody @Valid CoordinatesRequest parameter) {
        return calculatorService.getTotalDistance(parameter);
    }
}
