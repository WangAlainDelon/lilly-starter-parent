package org.lilly.core.config;

import org.lilly.core.utils.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Created by wangxiang on 2020/8/6
 *  密码登录的配置代码，只配置密码相关的
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationSuccessHandler lillyAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler lillyAuthenticationFailureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage(SecurityConstant.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstant.AUTHENTICATION_FORM_LOGIN)
                .successHandler(lillyAuthenticationSuccessHandler)
                .failureHandler(lillyAuthenticationFailureHandler);
    }
}
