package com.xiang.config;

import com.xiang.support.SmsCodeAuthenticationFilter;
import com.xiang.support.SmsCodeAuthenticationProvider;
import com.xiang.support.SmsCodeLoginConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Configuration
public class SecurityConfig {
    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http,
                                                          AuthenticationSuccessHandler authenticationSuccessHandler,
                                                          AuthenticationFailureHandler authenticationFailureHandler,
                                                          AuthenticationEntryPoint authenticationEntryPoint,
                                                          //声明一下用哪个SecurityContextRepository
                                                          @Qualifier("frameworkJwtSecurityContextRepository") SecurityContextRepository securityContextRepository)
            throws Exception {
        http.
                formLogin(formLogin -> formLogin
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login")
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler)
                )
                .csrf(csrf -> csrf.disable())
                //配置权限
                //.authorizeHttpRequests(author -> author.requestMatchers("/admin/**").hasRole("ADMIN"))
                .authorizeHttpRequests(author -> author.anyRequest().authenticated())
                //配置登录入口
                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint))
                //requireExplicitSave现在默认为true
                .securityContext(s -> s.securityContextRepository(securityContextRepository));


        Field filterOrdersField = HttpSecurity.class.getDeclaredField("filterOrders");
        filterOrdersField.setAccessible(true);
        Object filterRegistration = filterOrdersField.get(http);
        Method putMethod = filterRegistration.getClass().getDeclaredMethod("put", Class.class, int.class);
        putMethod.setAccessible(true);
        //要把SmsCodeAuthenticationFilter的order放在UsernamePasswordAuthenticationFilter附近
        //因为UsernamePasswordAuthenticationFilter是1900，所以将其order设为1901
        putMethod.invoke(filterRegistration, SmsCodeAuthenticationFilter.class, 1901);

        SmsCodeLoginConfigurer<HttpSecurity> httpSecuritySmsCodeLoginConfigurer = new SmsCodeLoginConfigurer<>("/smsCodeLogin");
        httpSecuritySmsCodeLoginConfigurer
                .loginProcessingUrl("/smsCodeLogin")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);

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
