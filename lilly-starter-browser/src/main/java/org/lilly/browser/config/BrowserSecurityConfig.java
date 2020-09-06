package org.lilly.browser.config;

import org.lilly.browser.authentication.LillyAuthenticationFailureHandler;
import org.lilly.browser.authentication.LillyAuthenticationSuccessHandler;
import org.lilly.core.config.AbstractChannelSecurityConfig;
import org.lilly.core.config.SmsCodeAuthenticationSecurityConfig;
import org.lilly.core.config.ValidateCodeSecurityConfig;
import org.lilly.core.properties.SecurityProperties;
import org.lilly.core.utils.SecurityConstant;
import org.lilly.core.validate.filter.ImageAndSmsValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * User: Mr.Wang
 * Date: 2020/5/31
 * AbstractChannelSecurityConfig 直接继承表单登录配置的公共代码
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

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

    @Autowired
    private SpringSocialConfigurer lillySpringSocialConfigurer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter(lillyAuthenticationFailureHandler, securityProperties);
//        SmsValidateCodeFilter smsValidateCodeFilter = new SmsValidateCodeFilter(lillyAuthenticationFailureHandler, securityProperties);
//        validateCodeFilter.afterPropertiesSet();
        //表单登录基本配置
        applyPasswordAuthenticationConfig(http);
        //浏览器需要的配置
        http
                .apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig).
                 and()
                .apply(lillySpringSocialConfigurer)
                .and()
                .rememberMe()
                .tokenRepository(tokenRepository())
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds()).and()
                .authorizeRequests()
                .antMatchers("/index",
                        "/authentication/require",
                        "/authentication/form",
                        "/authentication/mobile",
                        "/code/*",
//                        "/favicon.ico",
//                        "/fonts/**",
//                        "/js/*",
//                        "/images/*",
//                        "/**",
//                        "/css/*",
//                        "/vendor/jquery/*",
//                        "/vendor/bootstrap/css/*",
                        "/user/regist",
                        securityProperties.getBrowser().getSignUpUrl(),
                        securityProperties.getBrowser().getLoginPage())  //允许不登陆就可以访问的方法，多个用逗号分隔
                .permitAll()
                .anyRequest().authenticated(); //其他的需要授权后访问;


//        http
//                .authorizeRequests().antMatchers("/index",
//                "/authentication/require",
//                "/authentication/form",
//                "/authentication/mobile",
//                "/code/*",
//                securityProperties.getBrowser().getLoginPage())  //允许不登陆就可以访问的方法，多个用逗号分隔
//                .permitAll()
//                .anyRequest().authenticated()  //其他的需要授权后访问
//                .and()
//                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
////                .addFilterBefore(smsValidateCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin()   //使用form表单post方式进行登录
//                .loginPage("/authentication/require")       //自定义登录页面的跳转
//                .loginProcessingUrl(SecurityConstant.AUTHENTICATION_FORM_LOGIN) //表单登陆提交的登陆请求地址
//                .successHandler(lillyAuthenticationSuccessHandler) //登录成功事件处理
////                .successForwardUrl("/hello") 设置登录成功跳转页面
////                .failureUrl("/login?error=true") //error=true控制页面错误信息的展示
//                .failureHandler(lillyAuthenticationFailureHandler)
//                .permitAll()
//                .and()
//                .rememberMe()
//                .tokenRepository(tokenRepository())
//                .userDetailsService(userDetailsService)
//                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
//                .and()
//                .apply(smsCodeAuthenticationSecurityConfig);

        //关闭打开的csrf保护
//        http.cors().and().csrf().disable();
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
