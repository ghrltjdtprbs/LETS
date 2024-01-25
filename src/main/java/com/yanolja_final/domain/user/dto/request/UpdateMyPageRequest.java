package com.yanolja_final.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateMyPageRequest(
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = " 010-0000-0000 형식으로 입력해주세요 ")
    String phone,

    @Size(min = 1, message = "주소를 입력해 주세요")
    String addr1,

    @Size(min = 1, message = "상세 주소를 입력해 주세요")
    String addr2,

    @Size(min = 1, message = "우편 번호를 입력해 주세요.")
    String postCode
) {

}
