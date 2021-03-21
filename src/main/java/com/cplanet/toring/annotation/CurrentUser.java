package com.cplanet.toring.annotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.lang.annotation.*;


// 현재 로그인한 사용자에게 접근 할 수 있는 사용자 정의 Annotation
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {

}
