package com.yanolja_final.domain.packages.dto.response;

import com.yanolja_final.domain.user.entity.User;
import java.util.Optional;

public record MyInfoDto(
    String username,
    String email,
    String phone
) {

    public static MyInfoDto from(User user) {
        if (user == null) return null;
        return new MyInfoDto(
            user.getUsername(),
            user.getEmail(),
            user.getPhoneNumber()
        );
    }
}
