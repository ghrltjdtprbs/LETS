package com.yanolja_final.domain.user.dto.request;

import com.yanolja_final.domain.user.entity.User;
import com.yanolja_final.domain.user.exception.DuplicatedCurrentPasswordException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

public record UpdatePasswordRequest(

    @NotNull(message = "비밀 번호를 입력해 주세요.")
    @Size(min = 6, message = "비밀 번호는 6자 이상이어야 합니다.")
    String password
) {
    public void updatePassword(User user, PasswordEncoder passwordEncoder) {
        String currentEncryptedPassword = user.getEncryptedPassword();
        String newEncryptedPassword = passwordEncoder.encode(password);

        if (!passwordEncoder.matches(password, currentEncryptedPassword)) {
            user.updatePassword(newEncryptedPassword);
        } else {
            throw new DuplicatedCurrentPasswordException();
        }
    }
}
