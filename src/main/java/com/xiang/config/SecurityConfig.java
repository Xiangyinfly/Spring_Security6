package com.xiang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {
    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http,
                                                          AuthenticationSuccessHandler authenticationSuccessHandler,
                                                          AuthenticationFailureHandler authenticationFailureHandler,
                                                          AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .csrf()
                .disable()
                //配置权限
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                //配置登录入口
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> {
//
//        }
//    }
}
