package org.lilly.core.authentication.config;


import org.lilly.core.authentication.mobile.SmsAuthenticationFilter;
import org.lilly.core.authentication.mobile.SmsAuthenticationProvider;
import org.lilly.core.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.Component;

/**
 * 自定义的短信验证码登录security配置，
 * SecurityConfigurerAdapter用于将自定义的校验组织组件串起来
 */
@Component
public class SmsSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        super.configure(httpSecurity);
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        //为sms filter设置Manager组件  Manager不是我们自己写的而且在整个系统中就只有一个，所以这里需要拿到系统中的Manager
        //sharedObject 存放的就是顶级对象，用于获取所有的运行时信息。
        smsAuthenticationFilter.setAuthenticationManager(httpSecurity.getSharedObject(AuthenticationManager.class));
        //为sms filter设置 校验成功的处理
        smsAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        //为sms filter 设置校验失败的处理
        smsAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        //设置Provider,并且将sms过滤器加到过滤器链中
        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setMyUserDetailsService(myUserDetailsService);
        //authenticationProvider的provider我们可以自定义 即便是表单登录
        httpSecurity.authenticationProvider(smsAuthenticationProvider)
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


    }
}
