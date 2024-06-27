package com.aljjabaegi.geo.calculator.common.calculator;


import com.aljjabaegi.geo.calculator.common.calculator.record.Coordinate;
import com.aljjabaegi.geo.calculator.common.calculator.record.LineCoordinate;
import com.aljjabaegi.geo.calculator.domain.distance.record.response.DistanceCalculationResponse;
import com.aljjabaegi.geo.calculator.domain.coordinateCalculation.record.response.FootOfPerpendicularResponse;
import com.aljjabaegi.geo.calculator.domain.summary.record.response.BboxResponse;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

/**
 * 지도 관련 계산 인터페이스
 *
 * @author GEONLEE
 * @since 2024-06-21
 */
public interface GeoCalculator {
    //지구 반지름
    BigDecimal RADIUS = BigDecimal.valueOf(6371000);
    //기본 BigDecimal Math context 자리수 20자리까지, 반올림
    MathContext MATH_CONTEXT = new MathContext(20, RoundingMode.HALF_UP);

    //삼각함수를 활용하기 위해 radian 으로 변환
    default BigDecimal degreeToRadian(BigDecimal degree) {
        return degree.multiply(BigDecimal.valueOf(Math.PI).divide(BigDecimal.valueOf(180), MATH_CONTEXT));
    }

    //sin 함수
    default BigDecimal sin(BigDecimal value) {
        return BigDecimal.valueOf(Math.sin(value.doubleValue()));
    }

    //cos 함수
    default BigDecimal cos(BigDecimal value) {
        return BigDecimal.valueOf(Math.cos(value.doubleValue()));
    }

    //atan2 함수
    default BigDecimal atan2(BigDecimal value1, BigDecimal value2) {
        return BigDecimal.valueOf(Math.atan2(value1.doubleValue(), value2.doubleValue()));
    }

    //sqrt 함수
    default BigDecimal sqrt(BigDecimal value) {
        return value.sqrt(MATH_CONTEXT);
    }

    //두 지점 좌표간 거리 계산 메서드
    double calculateDistanceBetweenCoordinate(Coordinate firstCoordinate, Coordinate secondCoordinate);

    List<DistanceCalculationResponse> calculateDistanceBetweenCoordinates(List<Coordinate> coordinateList);

    //지점 좌표와 두선의 시작-종료 지점좌표로 수선의 발 지점 좌표를 구한다.
    Coordinate getCoordinateOnLine(
            Coordinate pointCoordinate, Coordinate startPointCoordinate, Coordinate endPointCoordinate);

    //최적 수선의 발 좌표를 구한다. (A-B, B-A 선의 수선의 발 지점이 다를 수 있음)
    FootOfPerpendicularResponse getFootOfPerpendicular(Coordinate pointCoordinate, List<LineCoordinate> lineVertexes);

    //선의 버텍스 정보와 선 위의 점 정보를 받아 선 위의 점까지의 거리를 구한다.
    Double getDistanceToPontOnLine(Coordinate pointCoordinate, List<LineCoordinate> lineVertexes);

    //좌표들의 중심 점을 구한다.
    Coordinate getCenterCoordinate(List<Coordinate> coordinates);

    //좌표들의 최대 최소 좌표를 구한다.
    BboxResponse getBboxCoordinate(List<Coordinate> coordinates);
}
