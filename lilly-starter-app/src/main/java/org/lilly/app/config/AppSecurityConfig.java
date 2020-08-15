package org.lilly.app.config;

import javax.sql.DataSource;
import org.lilly.core.config.AbstractChannelSecurityConfig;
import org.lilly.core.config.SmsCodeAuthenticationSecurityConfig;
import org.lilly.core.config.ValidateCodeSecurityConfig;
import org.lilly.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * User: Mr.Wang
 * Date: 2020/5/31
 * AbstractChannelSecurityConfig 直接继承表单登录配置的公共代码
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;


    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //浏览器需要的配置
        http
                .authorizeRequests().antMatchers("/index",
                "/authentication/require",
                "/authentication/form",
                "/authentication/mobile",
                "/code/*",
                securityProperties.getBrowser().getLoginPage())  //允许不登陆就可以访问的方法，多个用逗号分隔
                .permitAll()
                .anyRequest().authenticated(); //其他的需要授权后访问
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //第二次启动时需要注释掉这行代码
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

}
