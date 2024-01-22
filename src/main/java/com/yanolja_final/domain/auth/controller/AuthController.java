package com.yanolja_final.domain.auth.controller;

import com.yanolja_final.domain.auth.controller.request.LoginRequest;
import com.yanolja_final.domain.auth.facade.AuthFacade;
import com.yanolja_final.domain.auth.dto.TokenDTO;
import com.yanolja_final.global.common.CookieUtils;
import com.yanolja_final.global.util.ResponseDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class AuthController {

    public static final String ACCESS_TOKEN_COOKIE_NAME = "accessToken";

    private final CookieUtils cookieUtils;

    private final AuthFacade authFacade;

    @PostMapping("/email/login")
    public ResponseEntity<ResponseDTO<Void>> login(
        @RequestBody @Valid LoginRequest loginRequest,
        HttpServletResponse response
    ) {
        TokenDTO tokenDTO = authFacade.login(loginRequest);

        Cookie accessToken = cookieUtils.makeCookie(
            ACCESS_TOKEN_COOKIE_NAME, tokenDTO.accessToken()
        );
        response.addCookie(accessToken);

        return ResponseEntity
            .ok(ResponseDTO.ok());
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO<Void>> logout(HttpServletResponse response) {
        Cookie expiredCookie = cookieUtils.expireCookie(ACCESS_TOKEN_COOKIE_NAME);
        response.addCookie(expiredCookie);

        return ResponseEntity.ok(ResponseDTO.ok());
    }
}
