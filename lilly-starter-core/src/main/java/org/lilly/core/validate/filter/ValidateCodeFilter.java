package org.lilly.core.validate.filter;

import org.apache.commons.lang.StringUtils;
import org.lilly.core.properties.SecurityProperties;
import org.lilly.core.validate.code.image.ImageCode;
import org.lilly.core.validate.code.ValidateException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Mr.Wang
 * Date: 2020/6/9
 * 校验验证码的过滤器 OncePerRequestFilter 只会执行一次的过滤器
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

    private static final String SESSION_KEY = "SESSION_CODE_";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    Set<String> urls = new HashSet<>();
    private AuthenticationFailureHandler authenticationFailureHandler;
    private SecurityProperties securityProperties;

    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler, SecurityProperties securityProperties) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        //如果是登录的请求，从请求中拿到验证码和从session中拿到的验证码做对比
        //如果是登录的请求，和配置的验证请求都需要拦截下来做验证码的校验
//        boolean action = false;
//        for (String url : urls) {
//            if (antPathMatcher.match(url, httpServletRequest.getRequestURI())) {
//                action = true;
//            }
//        }
        if (StringUtils.equalsIgnoreCase("/authentication/form", httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "post")) {
            try {
                validate(httpServletRequest);
            } catch (ValidateException e) {
                //如果有异常，用登录失败异常处理器,这里必须处理AuthenticationException
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                //验证码校验有异常必须要返回，不然还会去验用户名和密码。
                return;
            }
        }
        //没有异常 登录成功放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void validate(HttpServletRequest httpServletRequest) throws ServletRequestBindingException {
        String key = SESSION_KEY + "IMAGE".toUpperCase();
        ImageCode imageCodeInSession = (ImageCode) sessionStrategy.getAttribute(new ServletWebRequest(httpServletRequest), key);
        //拿到post请求中的参数
        String code = ServletRequestUtils.getStringParameter(httpServletRequest, "imageCode");
        if (StringUtils.isBlank(code)) {
            throw new ValidateException("验证码不能为空");
        }
        if (imageCodeInSession == null) {
            throw new ValidateException("验证码不存在");
        }
        if (imageCodeInSession.isExpire()) {
            sessionStrategy.removeAttribute(new ServletWebRequest(httpServletRequest), key);
            throw new ValidateException("验证码已过期");
        }
        if (!StringUtils.equals(imageCodeInSession.getCode(), code)) {
            throw new ValidateException("验证码不正确");
        }
        sessionStrategy.removeAttribute(new ServletWebRequest(httpServletRequest), key);
    }

//    @Override
//    public void afterPropertiesSet() throws ServletException {
//        super.afterPropertiesSet();
//        String url = securityProperties.getValidateCode().getImageCode().getUrl();
//        String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(url, ",");
//        for (int i = 0; i < strings.length; i++) {
//            urls.add(strings[i]);
//        }
//        //加入登录请求
//        urls.add("/authentication/form");
//    }


    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
