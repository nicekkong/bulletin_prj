package com.cplanet.toring.wrapper;

import com.cplanet.toring.component.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;



public class RequestWrapper extends HttpServletRequestWrapper {

    static final String ACCESS_TOKEN = "access_token";

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

//    public String getAccessToken() {
//        Cookie[] cookies = getCookies();
//        String accessToken = null;
//        if(cookies == null) {
//            accessToken = ((HttpServletRequest) this.getRequest()).getHeader("Authorization");
//            if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
//                accessToken = accessToken.substring(7, accessToken.length());
//            }
//        } else {
//            for (Cookie c : cookies) {
//                if (ACCESS_TOKEN.equals(c.getName())) {
//                    accessToken = c.getValue();
//                    continue;
//                }
//            }
//        }
//        return accessToken;
//    }
}
