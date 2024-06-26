package com.aljjabaegi.geo.calculator.common.calculator;

import com.aljjabaegi.geo.calculator.common.exception.code.CommonErrorCode;
import com.aljjabaegi.geo.calculator.common.exception.custom.ServiceException;
import com.aljjabaegi.geo.calculator.domain.distance.record.Coordinate;
import com.aljjabaegi.geo.calculator.domain.distance.record.LineCoordinate;
import com.aljjabaegi.geo.calculator.domain.distance.record.request.PointDistanceRequest;
import com.aljjabaegi.geo.calculator.domain.distance.record.response.CalculatedDistanceResponse;
import com.aljjabaegi.geo.calculator.domain.distance.record.response.DistanceCalculationResponse;
import com.aljjabaegi.geo.calculator.domain.footOfPerpendicular.record.FootOfPerpendicularResponse;
import com.aljjabaegi.geo.calculator.domain.setting.record.SettingModifyRequest;
import com.aljjabaegi.geo.calculator.domain.setting.record.SettingModifyResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 노선의 지도관련 정보 가공 시 활용되는 계산기
 *
 * @author GEONLEE
 * @since 2024-06-21
 */
@Component
public class GeoCalculatorImpl implements GeoCalculator {

    //거리 표출 소수점
    private int distanceScale = 1;
    //좌표 표출 소수점
    private int coordinateScale = 8;

    //계산에 필요한 세팅 값을 설정
    public SettingModifyResponse modifySettings(SettingModifyRequest parameter) {
        if(!ObjectUtils.isEmpty(parameter.distanceScale())) {
            this.distanceScale = parameter.distanceScale();
        }
        if(!ObjectUtils.isEmpty(parameter.coordinateScale())) {
            this.coordinateScale = parameter.coordinateScale();
        }
        return SettingModifyResponse.builder()
                .distanceScale(this.distanceScale)
                .coordinateScale(this.coordinateScale)
                .build();
    }

    /**
     * 두 좌표 간 거리 계산
     *
     * @param firstCoordinate  첫 번째 지점 좌표
     * @param secondCoordinate 두 번째 지점 좌표
     * @return 소수점 1자리 double 거리 값
     * @author GEONLEE
     * @since 2024-06-24
     */
    @Override
    public double calculateDistanceBetweenCoordinate(Coordinate firstCoordinate, Coordinate secondCoordinate) {
        try {
            if (firstCoordinate.longitude() < 0 || firstCoordinate.latitude() < 0
                    || secondCoordinate.longitude() < 0 || secondCoordinate.latitude() < 0) {
                throw new ServiceException(CommonErrorCode.INVALID_PARAMETER, "음수인 좌표가 존재합니다.");
            }
            BigDecimal latitudeRadian = degreeToRadian(
                    BigDecimal.valueOf(secondCoordinate.latitude())
                            .subtract(BigDecimal.valueOf(firstCoordinate.latitude())));
            BigDecimal longitudeRadian = degreeToRadian(
                    BigDecimal.valueOf(secondCoordinate.longitude())
                            .subtract(BigDecimal.valueOf(firstCoordinate.longitude())));
            BigDecimal squareRootOfLatitudeSin = sin(latitudeRadian.divide(BigDecimal.valueOf(2), MATH_CONTEXT)).pow(2, MATH_CONTEXT);
            BigDecimal squareRootOfLongitudeSin = sin(longitudeRadian.divide(BigDecimal.valueOf(2), MATH_CONTEXT)).pow(2, MATH_CONTEXT);
            BigDecimal a = squareRootOfLatitudeSin
                    .add(
                            cos(degreeToRadian(BigDecimal.valueOf(firstCoordinate.latitude())))
                                    .multiply(cos(degreeToRadian(BigDecimal.valueOf(secondCoordinate.latitude()))))
                                    .multiply(squareRootOfLongitudeSin)
                    );
            BigDecimal c = BigDecimal.valueOf(2).multiply(atan2(sqrt(a), sqrt(BigDecimal.ONE.subtract(a))), MATH_CONTEXT);
            return RADIUS.multiply(c).setScale(this.distanceScale, RoundingMode.HALF_UP).doubleValue();
        } catch (NullPointerException e) {
            throw new ServiceException(CommonErrorCode.INVALID_PARAMETER, "좌표 정보가 올바르지 않습니다.");
        }
    }

    @Override
    public List<DistanceCalculationResponse> calculateDistanceBetweenCoordinates(List<Coordinate> coordinateList) {
        if (ObjectUtils.isEmpty(coordinateList)) {
            throw new ServiceException(CommonErrorCode.INVALID_PARAMETER, "거리를 계산할 좌표 정보가 존재하지 않습니다.");
        }
        if (coordinateList.size() < 2) {
            throw new ServiceException(CommonErrorCode.INVALID_PARAMETER, "최소 2개 이상의 좌표 정보가 필요합니다.");
        }
        List<DistanceCalculationResponse> distanceCalculationResponseList = new ArrayList<>();
        for (int i = 1, n = coordinateList.size(); i < n; i++) {
            Coordinate startCoordinate = coordinateList.get(i - 1);
            Coordinate endCoordinate = coordinateList.get(i);
            distanceCalculationResponseList.add(
                    DistanceCalculationResponse.builder()
                            .startCoordinate(startCoordinate)
                            .endCoordinate(endCoordinate)
                            .distance(this.calculateDistanceBetweenCoordinate(startCoordinate, endCoordinate))
                            .build()
            );
        }
        return distanceCalculationResponseList;
    }

    /**
     * 시작 종료 좌표를 갖는 직선과 한 지점 좌표의 수선의 발 지점 좌표 값을 구한다.
     *
     * @param pointCoordinate      한 지점 좌표
     * @param startPointCoordinate 직선의 시작 좌표
     * @param endPointCoordinate   직선의 종료 좌표
     * @return 직선과 한 점간 수선의 발과 거리 값
     * @author GEONLEE
     * @since 2024-06-24<br />
     * 2024-06-24 직선의 기울기가 0 이거나 무한대일 경우 조건 변경 BigDecimal.ZERO 설정 시 조건에 걸리지 않는 문제
     */
    @Override
    public Coordinate getCoordinateOnLine(Coordinate pointCoordinate, Coordinate startPointCoordinate, Coordinate endPointCoordinate) {
        try {
            BigDecimal subtractLatitude = BigDecimal.valueOf(endPointCoordinate.latitude()).subtract(BigDecimal.valueOf(startPointCoordinate.latitude()), MATH_CONTEXT);
            BigDecimal subtractLongitude = BigDecimal.valueOf(endPointCoordinate.longitude()).subtract(BigDecimal.valueOf(startPointCoordinate.longitude()), MATH_CONTEXT);
            BigDecimal lineInclination = null; // 두 점을 지나는 직선의 기울기
            BigDecimal latitudeIntercept; //기울기와 한점의 좌표로 구한 y 절편 (y=ax+b)
            BigDecimal crossing; // 두 점을 지나는 직선을 직교
            BigDecimal resultLongitude, resultLatitude; //결과 값
            if (subtractLatitude.doubleValue() == 0) {
                //직선의 기울기가 0일 경우 (수평)
                resultLongitude = BigDecimal.valueOf(pointCoordinate.longitude());
                resultLatitude = BigDecimal.valueOf(startPointCoordinate.latitude());
            } else if (subtractLongitude.doubleValue() == 0) {
                //직선의 기울기가 무한대 (infinite) 일 경우 (수직)
                resultLongitude = BigDecimal.valueOf(startPointCoordinate.longitude());
                resultLatitude = BigDecimal.valueOf(pointCoordinate.latitude());
            } else {
                //두 점을 지나는 직선의 기울기
                lineInclination = subtractLatitude.divide(subtractLongitude, MATH_CONTEXT);
                //기울기와 한 점의 좌표로 y 절편 구하기 y=ax+b
                latitudeIntercept = BigDecimal.valueOf(startPointCoordinate.latitude())
                        .subtract(
                                lineInclination.multiply(BigDecimal.valueOf(startPointCoordinate.longitude()), MATH_CONTEXT)
                        );
                //두 점을 지나는 직선을 직교하는 직선의 y 절편 y=-x/a+c
                crossing = BigDecimal.valueOf(pointCoordinate.latitude()).add(
                        BigDecimal.valueOf(pointCoordinate.longitude()).divide(lineInclination, MATH_CONTEXT)
                );
                resultLongitude = (crossing.subtract(latitudeIntercept)).divide(lineInclination.add(BigDecimal.ONE.divide(lineInclination, MATH_CONTEXT)), MATH_CONTEXT);
                resultLatitude = (BigDecimal.valueOf(-1).divide(lineInclination, MATH_CONTEXT).multiply(resultLongitude)).add(crossing);
            }

            //범위 내 수선의 발만 구하기 위한 조건
            if (lineInclination == null) {
                if (endPointCoordinate.latitude() <= resultLatitude.doubleValue() && startPointCoordinate.latitude() >= resultLatitude.doubleValue()
                        && endPointCoordinate.longitude() >= resultLongitude.doubleValue() && startPointCoordinate.latitude() <= resultLongitude.doubleValue()) {
                    return Coordinate.builder()
                            .longitude(resultLongitude.setScale(this.coordinateScale, RoundingMode.HALF_UP).doubleValue())
                            .latitude(resultLatitude.setScale(this.coordinateScale, RoundingMode.HALF_UP).doubleValue())
                            .build();
                }
            } else if (lineInclination.doubleValue() > 0) {
                if (startPointCoordinate.longitude() >= resultLongitude.doubleValue() && resultLongitude.doubleValue() >= endPointCoordinate.longitude()
                        && startPointCoordinate.latitude() >= resultLatitude.doubleValue() && resultLatitude.doubleValue() >= endPointCoordinate.latitude()) {
                    return Coordinate.builder()
                            .longitude(resultLongitude.setScale(this.coordinateScale, RoundingMode.HALF_UP).doubleValue())
                            .latitude(resultLatitude.setScale(this.coordinateScale, RoundingMode.HALF_UP).doubleValue())
                            .build();
                }
            } else {
                if (startPointCoordinate.longitude() <= resultLongitude.doubleValue() && resultLongitude.doubleValue() <= endPointCoordinate.longitude()
                        && startPointCoordinate.latitude() >= resultLatitude.doubleValue() && resultLatitude.doubleValue() >= endPointCoordinate.latitude()) {
                    return Coordinate.builder()
                            .longitude(resultLongitude.setScale(this.coordinateScale, RoundingMode.HALF_UP).doubleValue())
                            .latitude(resultLatitude.setScale(this.coordinateScale, RoundingMode.HALF_UP).doubleValue())
                            .build();
                }
            }
        } catch (NullPointerException e) {
            throw new ServiceException(CommonErrorCode.INVALID_PARAMETER, "없거나 올바르지 않은 좌표 정보가 존재합니다.");
        }
        return null;
    }

    /**
     * 최적 수선의 발 지점 추출
     *
     * @param pointCoordinate 수선의 발을 내릴 한 지점 좌표
     * @param lineVertexes    직선 좌표 버텍스 리스트
     * @return 최적 수선의 발 지점 좌표와 거리
     * @author GEONLEE
     * @since 2024-06-24
     */
    @Override
    public FootOfPerpendicularResponse getFootOfPerpendicular(Coordinate pointCoordinate, List<LineCoordinate> lineVertexes) {
        if (Objects.isNull(lineVertexes) || lineVertexes.size() < 2) {
            throw new ServiceException(CommonErrorCode.INVALID_PARAMETER, "직선의 좌표는 한개 이상이어야 합니다.");
        }
        //선의 좌표 순번으로 정렬
        lineVertexes = lineVertexes.stream().sorted((start, end) -> Math.toIntExact(start.pointSequence() - end.pointSequence())).toList();
        List<Coordinate> footOfPerpendicularCoordinateList = new ArrayList<>();
        double footOfPerpendicularLongitude = 0.0;
        double footOfPerpendicularLatitude = 0.0;
        double minDistance = Double.MAX_VALUE;
        /* 링크 보간점 간 양방향 선의 수선의 발 좌표를 구한다.
         * 단 방향 으로 구할 경우 곡선 의 수선의 발을 구할 수 없는 문제가 발생.
         * */
        for (int i = 1, n = lineVertexes.size(); i < n; i++) {
            LineCoordinate previousPoint = lineVertexes.get(i - 1);
            LineCoordinate currentPoint = lineVertexes.get(i);
            Coordinate forwardPointOnLink = getCoordinateOnLine(pointCoordinate,
                    Coordinate.builder()
                            .longitude(previousPoint.longitude())
                            .latitude(previousPoint.latitude())
                            .build(),
                    Coordinate.builder()
                            .longitude(currentPoint.longitude())
                            .latitude(currentPoint.latitude())
                            .build());

            if (forwardPointOnLink != null) {
                footOfPerpendicularCoordinateList.add(forwardPointOnLink);
            }
            Coordinate reversePointOnLink = getCoordinateOnLine(pointCoordinate,
                    Coordinate.builder()
                            .longitude(currentPoint.longitude())
                            .latitude(currentPoint.latitude())
                            .build(),
                    Coordinate.builder()
                            .longitude(previousPoint.longitude())
                            .latitude(previousPoint.latitude())
                            .build());
            if (reversePointOnLink != null) {
                footOfPerpendicularCoordinateList.add(reversePointOnLink);
            }
        }
        if (footOfPerpendicularCoordinateList.size() > 0) {
            //수선의 발을 구한 경우 점과 가장 가까운 점을 수선의 발로 정한다.
            for (Coordinate point : footOfPerpendicularCoordinateList) {
                double distance = calculateDistanceBetweenCoordinate(pointCoordinate, Coordinate.builder()
                        .longitude(point.longitude())
                        .latitude(point.latitude())
                        .build());
                if (minDistance > distance) {
                    minDistance = distance;
                    footOfPerpendicularLongitude = point.longitude();
                    footOfPerpendicularLatitude = point.latitude();
                }
            }
        } else {
            //수선의 발을 구할 수 없는 경우 링크 보간점 의 가장 가까운 점으로 보완 한다.
            for (LineCoordinate lineVertex : lineVertexes) {
                double distance = calculateDistanceBetweenCoordinate(pointCoordinate, Coordinate.builder()
                        .longitude(lineVertex.longitude())
                        .latitude(lineVertex.latitude())
                        .build());
                if (minDistance > distance) {
                    minDistance = distance;
                    footOfPerpendicularLongitude = lineVertex.longitude();
                    footOfPerpendicularLatitude = lineVertex.latitude();
                }
            }
        }
        double distance = getDistanceToPontOnLine(Coordinate.builder()
                        .longitude(footOfPerpendicularLongitude)
                        .latitude(footOfPerpendicularLatitude)
                        .build()
                , lineVertexes);
        return FootOfPerpendicularResponse.builder()
                .FootOfPerpendicularCoordinate(Coordinate.builder()
                        .longitude(footOfPerpendicularLongitude)
                        .latitude(footOfPerpendicularLatitude)
                        .build())
                .distance(distance)
                .build();
    }

    /**
     * 직선의 버텍스 와 직선 내 수선의 발 지점을 받아 수선의 발 지점까지의 길이를 구한다.<br />
     * 정방향과-> 역방향<- 을 구해야 함
     *
     * @param pointCoordinate 직선 내 수선의 발 좌표 값
     * @param lineVertexes    직선의 좌표 버텍스 리스트
     * @return 직선 내 좌표(pointCoordinate) 까지의 거리
     * @author GEONLEE
     * @since 2024-06-24
     */
    @Override
    public Double getDistanceToPontOnLine(Coordinate pointCoordinate, List<LineCoordinate> lineVertexes) {
        double errorRange = 0.2; // 오차 감안 범위
        BigDecimal distance = BigDecimal.ZERO;
        for (int i = 1, n = lineVertexes.size(); i < n; i++) {
            double lineDistance = calculateDistanceBetweenCoordinate(
                    Coordinate.builder()
                            .longitude(lineVertexes.get(i - 1).longitude())
                            .latitude(lineVertexes.get(i - 1).latitude())
                            .build(),
                    Coordinate.builder()
                            .longitude(lineVertexes.get(i).longitude())
                            .latitude(lineVertexes.get(i).latitude())
                            .build());
            double previousLinePointToPointDistance = calculateDistanceBetweenCoordinate(
                    Coordinate.builder()
                            .longitude(lineVertexes.get(i - 1).longitude())
                            .latitude(lineVertexes.get(i - 1).latitude())
                            .build(),
                    Coordinate.builder()
                            .longitude(pointCoordinate.longitude())
                            .latitude(pointCoordinate.latitude())
                            .build());
            double pointToNextLinePointDistance = calculateDistanceBetweenCoordinate(
                    Coordinate.builder()
                            .longitude(lineVertexes.get(i).longitude())
                            .latitude(lineVertexes.get(i).latitude())
                            .build(),
                    Coordinate.builder()
                            .longitude(pointCoordinate.longitude())
                            .latitude(pointCoordinate.latitude())
                            .build());
            if (previousLinePointToPointDistance + pointToNextLinePointDistance > lineDistance - errorRange
                    && previousLinePointToPointDistance + pointToNextLinePointDistance < lineDistance + errorRange) {
                distance = distance.add(BigDecimal.valueOf(previousLinePointToPointDistance));
                break;
            }
            distance = distance.add(BigDecimal.valueOf(lineDistance));
        }
        return distance.setScale(this.distanceScale, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public Double getLengthOfLine(LineCoordinate lineVertexes) {
        return null;
    }

    @Override
    public CalculatedDistanceResponse getDistanceBetweenRoutePoints(List<PointDistanceRequest> pointList) {
        return null;
    }

    @Override
    public Coordinate getCenterCoordinate(List<Coordinate> coordinates) {
        return null;
    }
}
