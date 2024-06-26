package com.aljjabaegi.geo.calculator.domain.distance.record;

/**
 * 선의 버텍스 정보
 *
 * @author GEONLEE
 * @since 2024-06-21
 */
public record LineCoordinate(
        Long pointSequence,
        Double longitude,
        Double latitude
) {
}
