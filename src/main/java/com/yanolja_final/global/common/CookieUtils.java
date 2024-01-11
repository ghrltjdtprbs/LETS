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
        cookie.setAttribute("Samesite", "None");
        cookie.setDomain(domain);
        cookie.setSecure(false);

        return cookie;
    }
}
