package com.yanolja_final.global.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // EMAIL
    EMAIL_SENDING_FAILURE(HttpStatus.BAD_REQUEST, "이메일 전송에 실패했습니다."),
    EMAIL_TEMPLATE_LOAD_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 템플릿 로드에 실패했습니다."),
    EMAIL_VERIFY_FAILURE(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다."),

    // USER
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),
    USER_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    PHONE_NUMBER_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 등록된 핸드폰 번호입니다."),
    AGREE_IS_NOT_TRUE(HttpStatus.BAD_REQUEST, "이용약관에 동의해야 합니다."),

    // AUTH
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다."),
    DUPLICATED_CURRENT_PASSWORD(HttpStatus.BAD_REQUEST, "현재 비밀번호와 동일합니다."),

    // PACKAGE
    PACKAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 패키지입니다."),
    PACKAGE_DEPARTURE_OPTION_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 패키지 옵션입니다."),
    PACKAGE_DATE_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 패키지와 일치하는 날짜가 없습니다."),
    AVAILABLE_DATE_NOT_FOUND(HttpStatus.NOT_FOUND, "예약 가능한 날짜가 아닙니다."),

    // HASHTAG
    HASHTAG_NOT_FOUND(HttpStatus.NOT_FOUND, "해쉬태그를 찾을 수 없습니다."),

    // ORDER
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 주문입니다."),
    MAXIMUM_CAPACITY(HttpStatus.BAD_REQUEST, "예약 인원이 초과되었습니다."),
    INVALID_CANCEL_FEE_AGREEMENT(HttpStatus.BAD_REQUEST, "취소 수수료 동의는 반드시 true여야 합니다."),
    NO_GUESTS(HttpStatus.BAD_REQUEST,"예약 인원은 반드시 한 명 이상 필요합니다."),

    // WISH
    WISH_NOT_FOUND(HttpStatus.OK, "존재하지 않는 찜ID 입니다."),
    WISH_ALREADY_EXISTS(HttpStatus.OK, "이미 좋아요를 누른 패키지입니다."),

    // THEME
    THEME_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 테마 입니다."),

    // POLL
    POLL_NOT_FOUND(HttpStatus.BAD_REQUEST, "등록된 찬/반 토론 이벤트가 없습니다."),
    ALREADY_VOTED(HttpStatus.FORBIDDEN, "이미 투표에 참여 하셨습니다."),
    INVALID_OPTION(HttpStatus.BAD_REQUEST, "해당 응답은 잘못된 응답입니다."),
    NOT_VOTED(HttpStatus.BAD_REQUEST, "투표를 진행하지 않아 결과를 확인 할 수 없습니다."),

    // REVIEW
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰입니다."),
    UNAUTHORIZED_REVIEW_DELETION(HttpStatus.FORBIDDEN, "리뷰 삭제 권한이 없습니다."),
    UNAUTHORIZED_REVIEW_ACCESS(HttpStatus.FORBIDDEN, "리뷰 작성 권한이 없습니다."),
    REVIEW_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 리뷰를 작성하였습니다."),

    // NOTICE
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 공지사항ID 입니다."),

    // ADVERTISEMENT
    ADVERTISEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 패키지입니다."),

    //FAQ
    FAQ_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 자주 묻는 질문ID 입니다."),

    // 5xx
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
