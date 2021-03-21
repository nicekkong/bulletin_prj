package com.cplanet.toring.component;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class AccessTokenCookie {

    final static int ONE_MINUTE = 60;
    final static int ONE_HOUR = 60 * 60;

    public Cookie createAccessToken(String jwt) {
        Cookie cookie = new Cookie("access_token", jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(ONE_HOUR);
        cookie.setPath("/");
        return cookie;
    }

    public Cookie extensionCookie(String jwt) {
        Cookie cookie = new Cookie("access_token", jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(ONE_HOUR);
        cookie.setPath("/");

        return cookie;
    }


    public Cookie removeAccessCookie() {
        Cookie removeCookie = new Cookie("access_token", null);
        removeCookie.setMaxAge(0);
        removeCookie.setPath("/");

        return removeCookie;
    }
}
