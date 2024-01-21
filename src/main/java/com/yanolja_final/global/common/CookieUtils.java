package com.yanolja_final.global.common;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

    @Value("${cookie.domain}")
    private String domain;

    public Cookie makeCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "None");
        cookie.setDomain(domain);
        cookie.setSecure(true);

        return cookie;
    }

    public Cookie expireCookie(String name) {
        Cookie cookie = makeCookie(name, "");
        cookie.setMaxAge(0);
        return cookie;
    }
}
