package com.cplanet.toring.controller;

import com.cplanet.toring.component.JwtTokenProvider;
import com.cplanet.toring.domain.Member;
import com.cplanet.toring.domain.security.MemberPrincipal;
import com.cplanet.toring.wrapper.RequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController  {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    public BaseController() {
    }

    public String getAccessToken() {
        RequestWrapper request = new RequestWrapper(getServletRequest());
        return jwtTokenProvider.getJwtFromRequest(request);
    }

    public Member getMemberInfo() {
        MemberPrincipal memberPrincipal = MemberPrincipal.create(new Member());
        try {
            memberPrincipal = (MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            logger.error("token Error {}", e.getLocalizedMessage());
        }
        return memberPrincipal.getMember();
    }

    public Long getMemberId() {
        Member member = getMemberInfo();
        return member.getId();
    }

    protected HttpServletRequest getServletRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra == null || sra.getRequest() == null) {
            return null;
        }
        return sra.getRequest();
    }
}
