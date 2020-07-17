package org.lilly.core.validate.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.lilly.core.enums.ValidateCodeType;
import org.lilly.core.properties.SecurityProperties;
import org.lilly.core.utils.SecurityConstant;
import org.lilly.core.validate.ValidateCodeProcessorHolder;
import org.lilly.core.validate.code.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 图形验证码，短信验证码的校验Filter
 */
@Component("validateCodeFilter")
public class ImageAndSmsValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * 根据对应的URL类型获取对应校验处理器
     */
    private Map<String, ValidateCodeType> urlsMap = new HashMap<>();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //1.根据请求的url获取需要检验码校验的类型
        ValidateCodeType validateCodeType = urlsMap.get(httpServletRequest.getRequestURI());
        if (validateCodeType == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        //2.根据校验类型获取校验码的校验逻辑
        try {
            LOGGER.info("校验请求（" + httpServletRequest.getRequestURI() + "）中的验证码，验证码类型为" + validateCodeType);
            validateCodeProcessorHolder
                    .findValidateCodeProcessorByType(validateCodeType)
                    .validate(new ServletWebRequest(httpServletRequest, httpServletResponse));
        } catch (ValidateException e) {
            //如果有异常，用登录失败异常处理器,这里必须处理AuthenticationException
            authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
            //验证码校验有异常必须要返回，不然还会去验用户名和密码。
            return;

        }
    }

    /**
     * 初始化要拦截的URL配置信息
     *
     * @throws ServletException
     */

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //添加表单登录的请求
        urlsMap.put(SecurityConstant.AUTHENTICATION_FORM, ValidateCodeType.IMAGE);
        //添加用户配置的拦截url
        addUrlsToMap(securityProperties.getValidateCode().getImageCode().getUrl(), ValidateCodeType.IMAGE);

        //添加短信登录请求
        urlsMap.put(SecurityConstant.AUTHENTICATION_MOBILE, ValidateCodeType.SMS);
        addUrlsToMap(securityProperties.getValidateCode().getSmsCode().getUrl(), ValidateCodeType.SMS);

    }

    /**
     * 添加额外用户配置的额外的拦截url
     *
     * @param urls
     * @param type
     */
    private void addUrlsToMap(String urls, ValidateCodeType type) {
        if (StringUtils.isBlank(urls)) {
            return;
        } else {
            String[] split = StringUtils.splitByWholeSeparatorPreserveAllTokens(urls, ",");
            for (String url : split) {
                urlsMap.put(url, type);
            }
        }
    }
}
