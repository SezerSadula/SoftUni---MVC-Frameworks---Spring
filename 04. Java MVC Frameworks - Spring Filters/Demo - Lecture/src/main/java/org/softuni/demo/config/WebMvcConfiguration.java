package org.softuni.demo.config;

import org.softuni.demo.interceptors.CaptchaInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final HandlerInterceptor titleModifierInterceptor;
    private final CaptchaInterceptor captchaInterceptor;

    public WebMvcConfiguration(@Qualifier("title_modifier") HandlerInterceptor titleModifierInterceptor, CaptchaInterceptor captchaInterceptor) {
        this.titleModifierInterceptor = titleModifierInterceptor;
        this.captchaInterceptor = captchaInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titleModifierInterceptor);
        registry.addInterceptor(this.captchaInterceptor);
    }
}
