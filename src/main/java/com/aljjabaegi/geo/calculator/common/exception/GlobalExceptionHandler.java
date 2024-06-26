package com.aljjabaegi.geo.calculator.common.exception;

import com.aljjabaegi.geo.calculator.common.exception.code.CommonErrorCode;
import com.aljjabaegi.geo.calculator.common.exception.code.ErrorCode;
import com.aljjabaegi.geo.calculator.common.exception.custom.ServiceException;
import com.aljjabaegi.geo.calculator.common.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.SQLDataException;
import java.util.List;
import java.util.StringJoiner;

/**
 * 전역 예외 처리 클래스, Exception 별로 구분하여 처리
 * ResponseEntityExceptionHandler 를 extends 받아 처리하는 것이 효율적일지 고민 필요.
 *
 * @author GEONLEE
 * @since 2022-11-09<br />
 * 2022-11-14 GEONLEE - 응답 Header 에 Status, Content-type 추가, InternalAuthenticationServiceException (활성화, 권한정보 없음)<br />
 * - BadCredentialsException (id, pw 다름) 통합<br />
 * 2023-02-06 GEONLEE - IllegalArgumentException 에 같은 로직으로 PropertyReferenceException(org.springframework.data.mapping) 추가<br />
 * 2023-02-21 GEONLEE - InvalidDataAccessApiUsageException 추가<br />
 * 2023-02-22 GEONLEE - IOException 추가 (카카오, 네이버 로그인)<br />
 * 2023-03-02 GEONLEE - ConstraintViolationException 추가 (VALIDATION 관련)<br />
 * 2023-03-21 GEONLEE - DataIntegrityViolationException 응답 메시지를 서비스 오류로 변경(로그는 그대로)<br />
 * 2023-05-12 GEONLEE - KT 검증 사항 적용, HTTP 응답은 200, 자세한 상태코드는 BODY 메시지로 전달, MethodArgumentNotValidException 추가로 @Valid 공통 처리<br />
 * 2023-10-13 GEONLEE - ServiceException 분기 (전달받은 메시지로 처리 하기 위해) RuntimeException 주석 처리<br />
 * 2024-03-15 GEONLEE - @RestControllerAdvice 으로 변경, makeErrorResponse method 삭제, 모든 메서드에서 리턴 Object 를 ErrorResponse 로 변경<br />
 * 2024-03-20 GEONLEE - 전체 코드 정리<br />
 * 2024-03-26 GEONLEE - 500 에러 ClassCastException 추가 (AOP에서 발생 가능성 있음)<br />
 * 2024-03-29 GEONLEE - 코드체계 변경에 따른 처리<br />
 * 2024-04-26 GEONLEE - checked, unchecked exception 구분 하게 전체 변경<br />
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Checked Exception 관련 처리
     *
     * @author GEONLEE
     * @since 2024-04-02
     */
    @ExceptionHandler(value = {ServiceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //추가 시 Swagger Response 에 등록됨.
    public ResponseEntity<ErrorResponse> handleCheckedException(ServiceException e) {
        return handleExceptionInternal(e.errorCode, e);
    }

    /**
     * Unchecked Exception 관련 처리
     *
     * @author GEONLEE
     * @since 2024-04-02
     */
    @ExceptionHandler(value = {IOException.class, IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<ErrorResponse> handleUncheckedException(Exception e) {
        CommonErrorCode errorCode = CommonErrorCode.SERVICE_ERROR;
        return handleExceptionInternal(errorCode, e);
    }

    /**
     * DB Data type 과 일치하지 않거나, 범위를 벗어난 값이 전달 될 경우 처리
     *
     * @author GEONLEE
     * @since 2024-05-29
     */
    @ExceptionHandler(value = SQLDataException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ErrorResponse> handleSQLDataException(SQLDataException e) {
        CommonErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, e);
    }

    /**
     * Validation 관련 Exception 처리
     *
     * @author GEONLEE
     * @since 2024-04-02<br />
     * 2024-05-21 GEONLEE - validException detail message 처리 추가<br />
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder stringBuilder = new StringBuilder();
        StringJoiner stringJoiner = new StringJoiner(", ");
        LOGGER.error("======================@Valid Exception START======================");
        LOGGER.error("object : {}", e.getBindingResult().getObjectName());
        List<FieldError> fieldList = e.getFieldErrors();
        for (FieldError field : fieldList) {
            stringJoiner.add(field.getField() + ": " + field.getDefaultMessage());
        }
        stringBuilder.append(stringJoiner);
        LOGGER.error(stringBuilder.toString());
        LOGGER.error("======================@Valid Exception End========================");
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, new InvalidParameterException(stringJoiner.toString()));
    }

    /**
     * ErrorResponse return method
     *
     * @author GEONLEE
     * @since 2024-04-11<br />
     * 2024-05-21 GEONLEE detail message 추가<br />
     * 2024-05-24 GEONLEE - 개발 환경에서만 detailMessage 전송되도록 수정<br />
     */
    private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode, Exception e) {
        /* 모든 HTTP Status 코드는 200으로 전달하고 내부 코드를 상세히 전달 */
        LOGGER.error("[" + errorCode.getResultCode() + "] {}", errorCode, e);
        String detailMessage = (errorCode.getResultMsg().equals(e.getMessage())) ? null : e.getMessage();
        return ResponseEntity.ok()
                .header("Content-type", String.valueOf(MediaType.APPLICATION_JSON))
                .body(ErrorResponse.builder()
                        .status(errorCode.getResultCode())
                        .message(errorCode.getResultMsg())
                        .detailMessage(detailMessage).build());
    }
}
