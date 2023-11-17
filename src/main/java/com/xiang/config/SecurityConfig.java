package com.xiang.config;

import com.xiang.handler.FrameworkAuthenticationSuccessHandler;
import com.xiang.support.SmsCodeAuthenticationFilter;
import com.xiang.support.SmsCodeAuthenticationProvider;
import com.xiang.support.SmsCodeLoginConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

        SmsCodeLoginConfigurer<HttpSecurity> httpSecuritySmsCodeLoginConfigurer = new SmsCodeLoginConfigurer<>("/smsCodeLogin");
        httpSecuritySmsCodeLoginConfigurer
                .loginProcessingUrl("/smsCodeLogin")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);

        Field filterOrdersField = HttpSecurity.class.getDeclaredField("filterOrders");
        filterOrdersField.setAccessible(true);
        Object filterRegistration = filterOrdersField.get(http);
        Method putMethod = filterRegistration.getClass().getDeclaredMethod("put", Class.class, int.class);
        putMethod.setAccessible(true);
        //要把SmsCodeAuthenticationFilter的order放在UsernamePasswordAuthenticationFilter附近
        //因为UsernamePasswordAuthenticationFilter是1900，所以将其order设为1901
        putMethod.invoke(filterRegistration, SmsCodeAuthenticationFilter.class,1901);

        http.apply(httpSecuritySmsCodeLoginConfigurer);
        http.authenticationProvider(new SmsCodeAuthenticationProvider());
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> {
//
//        }
//    }
}
