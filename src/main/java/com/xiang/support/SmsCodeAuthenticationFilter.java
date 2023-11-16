package com.xiang.support;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    //传一个sms登录的页面
    protected SmsCodeAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String mobilePhone = request.getParameter("mobilePhone");
        String code = request.getParameter("code");
        //创建token
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = new SmsCodeAuthenticationToken(mobilePhone, code);
        smsCodeAuthenticationToken.setDetails(this.authenticationDetailsSource.buildDetails(request));
        //进行认证并return结果
        return this.getAuthenticationManager().authenticate(smsCodeAuthenticationToken);
    }
}
