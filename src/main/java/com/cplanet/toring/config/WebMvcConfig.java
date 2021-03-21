package com.cplanet.toring.config;

import com.cplanet.toring.interceptor.LoginValidateInterceptor;
import com.cplanet.toring.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
//@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    private LoginValidateInterceptor loginValidateInterceptor;
    private RequestInterceptor requestInterceptor;

    public WebMvcConfig(LoginValidateInterceptor loginValidateInterceptor,
                        RequestInterceptor requestInterceptor
                        ) {
        this.loginValidateInterceptor = loginValidateInterceptor;
        this.requestInterceptor = requestInterceptor;
    }

    private final long MAX_AGE_SECS = 3600;

    // 로그인 세션 체크를 위한 interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginValidateInterceptor)
                .addPathPatterns("/**")
                // TODO : 로그인 없이 접근 가능한 URL 설정
                .excludePathPatterns("NO_LOGIN_URLS_PATTERN");

        // 로그 출력용
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**");

    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    /**
     * Resource 위치(js,css 등)를 설정
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE").maxAge(MAX_AGE_SECS);
    }
}
