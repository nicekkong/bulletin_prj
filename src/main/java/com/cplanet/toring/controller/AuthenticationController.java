package com.cplanet.toring.controller;


import com.cplanet.toring.annotation.CurrentUser;
import com.cplanet.toring.component.AccessTokenCookie;
import com.cplanet.toring.domain.Member;
import com.cplanet.toring.domain.security.MemberPrincipal;
import com.cplanet.toring.dto.ApiResponse;
import com.cplanet.toring.dto.response.JwtAuthenticationResponse;
import com.cplanet.toring.dto.request.LoginRequestDto;
import com.cplanet.toring.dto.request.SignUpRequest;
import com.cplanet.toring.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class AuthenticationController  extends BaseController {

    final private ModelMapper modelMapper;
    final private AccessTokenCookie accessTokenCookie;
    final private AuthenticationService authenticationService;

    public AuthenticationController(ModelMapper modelMapper,
                                    AuthenticationService authenticationService,
                                    AccessTokenCookie accessTokenCookie) {
        this.modelMapper = modelMapper;
        this.authenticationService = authenticationService;
        this.accessTokenCookie = accessTokenCookie;
    }

    static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {

        String jwt = authenticationService.makeLoginToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        response.addCookie(accessTokenCookie.createAccessToken(jwt));

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletResponse response) {

        Map<String, Object> result = new HashMap<>();

        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();

        Member member = modelMapper.map(signUpRequest, Member.class);
        ApiResponse signUpMember = authenticationService.signUpMember(member);

        // 정상적으로 회원 생성이 되면, 해당 사용자의 token값을 cookie로 생성한다.
        if(signUpMember.getSuccess()) {
            String jwt = authenticationService.makeLoginToken(email, password);
            response.addCookie(accessTokenCookie.createAccessToken(jwt));
            result.put("access_token", jwt);

//            response.addHeader("Content-Type", "application/json;charset=UTF-8");
//            response.addHeader("Access-Control-Allow-Credentials", "true");
//            response.addHeader("Access-Control-Allow-Origin", "local.nicekkong2.com");
//            response.addHeader("Access-Control-Allow-Headers",
//                    "Origin, X-Requested-With, Content-Type, Accept");

        }

        result.put("success", signUpMember.getSuccess());
        result.put("message", signUpMember.getMessage());


        return new ResponseEntity(result, signUpMember.getSuccess()? HttpStatus.OK : HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/auth/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        response.addCookie(accessTokenCookie.removeAccessCookie());
        return ResponseEntity.ok(new ApiResponse(true, "logout success"));
    }


    @GetMapping(value = "/member")
    public Member sampleMember(@CurrentUser MemberPrincipal member) {
        logger.info("access_token in cookie ::: {} ", this.getAccessToken());
        return member.getMember();
    }


    @GetMapping(value = "/memberInfo")
    public Member sampleMemberInfo() {

        logger.info("access_token in cookie ::: {} ", this.getAccessToken());
        if(getAccessToken() == null) {
            return new Member();
        }
        Member member = this.getMemberInfo();
        return member;
    }


}
