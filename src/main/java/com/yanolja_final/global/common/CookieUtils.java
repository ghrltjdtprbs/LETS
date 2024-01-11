package com.yanolja_final.global.common;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;

public class CookieUtils {

    @Value("${cookie.domain")
    private static String domain;

    private CookieUtils() {
    }

    public static Cookie makeCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setAttribute("Samesite", "None");

        cookie.setDomain(domain);
        cookie.setSecure(false);

        return cookie;
    }
}
