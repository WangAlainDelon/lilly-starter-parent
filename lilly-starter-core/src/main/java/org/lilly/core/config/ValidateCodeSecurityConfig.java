package org.lilly.core.config;

import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

/**
 * Created by wangxiang on 2020/8/6
 * 关于校验码的配置,校验验证码逻辑的
 */
@Component("validateCodeSecurityConfig")
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    /**
     * ImageAndSmsValidateCodeFilter
     */
    @Autowired
    private Filter imageAndSmsValidateCodeFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        //http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
        //AbstractPreAuthenticatedProcessingFilter 这个类将检查安全上下文的当前内容，如果为空，它将尝试从HTTP请求中提取用户信息并将其提交给AuthenticationManager
        // SmsAuthenticationFilter,UsernamePasswordAuthenticationFilter都继承此类
        http.addFilterBefore(imageAndSmsValidateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }
}
