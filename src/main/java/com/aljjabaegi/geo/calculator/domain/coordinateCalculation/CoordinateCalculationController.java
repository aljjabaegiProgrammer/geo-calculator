package com.aljjabaegi.geo.calculator.domain.footOfPerpendicular;

import com.aljjabaegi.geo.calculator.common.response.ItemResponse;
import com.aljjabaegi.geo.calculator.domain.footOfPerpendicular.record.FootOfPerpendicularRequest;
import com.aljjabaegi.geo.calculator.domain.footOfPerpendicular.record.FootOfPerpendicularResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GEONLEE
 * @since 2024-06-25
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "[GEO-002] 좌표 계산", description = "[담당자 : GEONLEE]")
public class FootOfPerpendicularController {

    private final FootOfPerpendicularService footOfPerpendicularService;

    @PostMapping(value = "/v1/foot-of-perpendicular", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "선과 한 좌표의 수선의 발(직교하는 지점) 좌표와 선의 시작 좌표 부터 수선의 발 좌표까지의 길이(m) 조회", description = """
            선과 한 지점 좌표 값을 받아 수선의 발 좌표를 구하고<br />
            선의 시작 좌표부터 수선의 발 좌표까지의 길이를 구하여 리턴한다.<br />
            한 지점 좌표가 선의 범위를 벗어난 경우 선의 좌표와 가장 가까운 지점(시작이나, 끝 좌표)을 수선의 발 지점으로 보완한다.
            """,
            operationId = "RM-002-01"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = {
                    @ExampleObject(name = "Sample",
                            value = """
                                    {
                                         "lineCoordinates":[
                                             {
                                               "pointSequence":1,
                                               "longitude": 126.7419211,
                                               "latitude": 35.98231874
                                             },
                                             {
                                               "pointSequence":2,
                                               "longitude": 126.7449,
                                               "latitude": 35.98235356
                                             }
                                         ],
                                         "coordinate": {
                                             "longitude":126.74325162,
                                             "latitude":35.98228349
                                         }
                                    }
                                     """
                    )}))
    public ResponseEntity<ItemResponse<FootOfPerpendicularResponse>> getDistanceBetweenPoints(@RequestBody FootOfPerpendicularRequest parameter) {
        return footOfPerpendicularService.getFootOfPerpendicular(parameter);
    }
}
