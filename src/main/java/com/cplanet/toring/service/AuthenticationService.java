package com.cplanet.toring.service;


import com.cplanet.toring.component.JwtTokenProvider;
import com.cplanet.toring.domain.Member;
import com.cplanet.toring.dto.ApiResponse;
import com.cplanet.toring.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    final static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    final private AuthenticationManager authenticationManager;
    final private MemberRepository memberRepository;
    final private MemberService memberService;
    final private JwtTokenProvider jwtTokenProvider;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                    MemberRepository memberRepository,
                                    MemberService memberService,
                                    JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String makeLoginToken(String email, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    public ApiResponse signUpMember(Member member) {

        logger.info("New Member ::: {}", member);

        if (memberRepository.existsByEmail(member.getEmail())) {
            logger.error("EMail [{}] is already exist!!", member.getEmail());
            return new ApiResponse(false, "Username is already taken!");
        }

        memberService.createMember(member);
        logger.info("[{}] signup successfully~!! ", member.getEmail());
        return new ApiResponse(true, "User registered successfully");

    }


}
