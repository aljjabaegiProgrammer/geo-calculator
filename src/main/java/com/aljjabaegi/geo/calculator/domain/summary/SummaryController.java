package com.aljjabaegi.geo.calculator.domain.summary;

import com.aljjabaegi.geo.calculator.common.response.ItemResponse;
import com.aljjabaegi.geo.calculator.common.calculator.record.Coordinate;
import com.aljjabaegi.geo.calculator.domain.distance.record.request.CoordinatesRequest;
import com.aljjabaegi.geo.calculator.domain.summary.record.response.BboxResponse;
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
 * @since 2024-06-26
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "[GEO-003] 좌표 요약 정보", description = "[담당자 : GEONLEE]")
@RequestMapping("/v1/summary")
public class SummaryController {
    private final SummaryService summaryService;

    @PostMapping(value = "/center", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "좌표들의 중심 좌표를 조회", description = """
            """,
            operationId = "GEO-003-01"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = {
                    @ExampleObject(name = "Sample",
                            value = """
                                    {
                                         "coordinates":[
                                             {
                                               "longitude": 126.7419211,
                                               "latitude": 35.98231874
                                             },
                                             {
                                               "longitude": 126.7449,
                                               "latitude": 35.98235356
                                             },
                                             {
                                               "longitude":126.74325162,
                                               "latitude":35.98228349
                                             }
                                         ]
                                    }
                                     """
                    )}))
    public ResponseEntity<ItemResponse<Coordinate>> getCenterCoordinate(@RequestBody @Valid CoordinatesRequest parameter) {
        return summaryService.getCenterCoordinate(parameter);
    }

    @PostMapping(value = "/bbox", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "좌표들의 최대 좌표, 최소 좌표를 조회", description = """
            """,
            operationId = "GEO-003-02"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = {
                    @ExampleObject(name = "Sample",
                            value = """
                                    {
                                         "coordinates":[
                                             {
                                               "longitude": 126.7419211,
                                               "latitude": 35.98231874
                                             },
                                             {
                                               "longitude": 126.7449,
                                               "latitude": 35.98235356
                                             },
                                             {
                                               "longitude":126.74325162,
                                               "latitude":35.98228349
                                             }
                                         ]
                                    }
                                     """
                    )}))
    public ResponseEntity<ItemResponse<BboxResponse>> getBboxCoordinate(@RequestBody @Valid CoordinatesRequest parameter) {
        return summaryService.getBboxCoordinate(parameter);
    }
}
