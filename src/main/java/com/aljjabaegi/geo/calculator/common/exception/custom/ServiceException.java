package com.aljjabaegi.geo.calculator.common.exception.custom;


import com.aljjabaegi.geo.calculator.common.exception.code.CommonErrorCode;
import com.aljjabaegi.geo.calculator.common.exception.code.CustomErrorCode;
import com.aljjabaegi.geo.calculator.common.exception.code.ErrorCode;

import java.io.Serial;

/**
 * RuntimeException 처리용 Exception<br />
 * Internal Server Exception 전달 방지용
 * 모든 서비스 로직에서 해당 Exception 을 throw
 *
 * @author GEONLEE
 * @since 2022-11-09<br />
 * 2023-05-03 GEONLEE - final 제거<br />
 * 2024-03-29 GEONLEE - 로그에 Exception message 표출되도록 수정<br />
 * 2024-04-12 GEONLEE - 1.3.0 개선사항 반영, 생성자 추가<br />
 */
public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public final ErrorCode errorCode;

    /**
     * CommonErrorCode 와 Exception 이 전달된 경우<br />
     * RuntimeException 으로 message 와 Exception 전달
     *
     * @author GEONLEE
     * @since 2024-04-02
     */
    public ServiceException(CommonErrorCode errorCode, Throwable cause) {
        super(cause.getMessage(), cause);
        this.errorCode = errorCode;
    }

    /**
     * CommonErrorCode, custom Message 가 전달된 경우<br />
     *
     * @author GEONLEE
     * @since 2024-04-11
     */
    public ServiceException(CommonErrorCode errorCode, String message) {
        super(message, null);
        this.errorCode = errorCode;
    }

    /**
     * CommonErrorCode 만 전달된 경우<br />
     *
     * @author GEONLEE
     * @since 2024-04-02<br />
     * RuntimeException 으로 message 전달 추가
     */
    public ServiceException(CommonErrorCode errorCode) {
        super(errorCode.getResultMsg(), null);
        this.errorCode = errorCode;
    }

    /**
     * CustomErrorCode 처리용 <br />
     *
     * @author GEONLEE
     * @since 2024-05-14<br />
     */
    public ServiceException(CustomErrorCode errorCode) {
        super(errorCode.getResultMsg(), null);
        this.errorCode = errorCode;
    }
}
