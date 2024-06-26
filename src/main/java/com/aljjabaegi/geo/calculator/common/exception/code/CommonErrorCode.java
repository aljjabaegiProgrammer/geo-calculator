package com.aljjabaegi.geo.calculator.common.exception.code;

import lombok.Getter;

/**
 * 프로젝트 내에서 발생하는 공통 Exception Code, Message 관리
 *
 * @author GEONLEE
 * @since 2022-11-09<br />
 * 2023-03-21 GEONLEE - ID_VALIDATION 메시지 수정<br />
 * 2023-05-03 GEONLEE - ID_VALIDATION 메시지 수정<br />
 * 2023-10-13 GEONLEE - WRONG_DATA 메시지 수정<br />
 * 2023-11-28 GEONLEE - EXISTED_ID HttpStatus code 변경<br />
 * 2024-03-15 GEONLEE - RESOURCE_NOT_FOUND 제거<br />
 * 2024-03-29 GEONLEE - DUPLICATION_LOGIN 추가<br />
 * - API Code String 으로 변경<br />
 * 2024-03-29 GEONLEE - 에러 코드 체계 정리<br />
 * 2024-05-14 GEONLEE - NO_DATA 추가<br />
 * 2024-05-17 GEONLEE - EXISTING_DATA 추가<br />
 */
@Getter
public enum CommonErrorCode implements ErrorCode {

    /*NS_ERR_01 -> Server exception*/
    SERVICE_ERROR("NS_ER_SV_01", "요청한 서비스에 문제가 발생했습니다. 잠시 후에 다시 시도해 주세요."), /*Unchecked Exception*/
    LOGIC_ERROR("NS_ER_SV_02", "데이터를 처리하는데 실패했습니다."), /*Checked exception*/
    NO_DATA("NS_NO_DT", "데이터가 존재하지 않습니다."),
    EXISTING_DATA("NS_EX_DT", "이미 존재하는 데이터 입니다."),
    /*NS_ERR_02 ->  Client exception*/
    URI_NOT_FOUND("NS_ER_CT_01", "요청하신 URI를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED("NS_ER_CT_02", "요청 메소드를 지원하지 않습니다."),
    UNSUPPORTED_MEDIA_TYPE("NS_ER_CT_03", "요청하신 컨텐트 타입을 지원하지 않습니다."),
    INVALID_PARAMETER("NS_ER_CT_04", "적합하지 않은 인자가 전달되었습니다."),
    REQUIRED_PARAMETER("NS_ER_CT_05", "적합하지 않은 인자가 전달되었습니다."),
    NULL_POINTER("NS_ER_CT_06", "적합하지 않은 인자가 전달되었습니다."),
    ENTITY_NOT_FOUND("NS_ER_CT_07", "적합하지 않은 인자가 전달되었습니다."),
    ENTITY_DUPLICATED("NS_ER_CT_08", "적합하지 않은 인자가 전달되었습니다."),
    /*NS_ERR_03 -> Authentication exception*/
    NOT_AUTHENTICATION("NS_ER_AT_01", "자격 증명에 실패하였습니다."),
    DUPLICATION_LOGIN("NS_ER_AT_02", "자격 증명에 실패하였습니다."),
    EXPIRED_TOKEN("NS_ER_AT_03", "자격 증명에 실패하였습니다."),
    NO_MATCHING_USER("NS_ER_AT_04", "자격 증명에 실패하였습니다."),
    WRONG_PASSWORD("NS_ER_AT_05", "자격 증명에 실패하였습니다."),
    LOCKED_MEMBER("NS_ER_AT_06", "잠긴 회원입니다. 관리자에게 문의하세요."),
    /*NS_ERR_04 -> Authorization*/
    FILE_WRITE_EXCEPTION("NS_ER_AT_07", "파일을 쓰는데 실패했습니다."),
    FORBIDDEN("NS_ER_FD", "사용자 접근이 거부 되었습니다.");

    private final String resultCode;
    private final String resultMsg;

    CommonErrorCode(String rc, String rm) {
        resultCode = rc;
        resultMsg = rm;
    }
}
