package com.yanolja_final.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;

public record UpdateMyPageRequest(
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = " 010-0000-0000 형식으로 입력해주세요 ")
    String phone,
    String addr1,
    String addr2,
    String postCode
) {

}
