package org.lilly.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lilly.core.enums.LoginType;
import org.lilly.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Mr.Wang
 * Date: 2020/6/8
 */
@Component
public class LillyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws
            ServletException {
        logger.info("登录成功");
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            try {
                httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication));
            } catch (IOException e) {
                try {
                    if (httpServletResponse.getWriter() != null) {
                        httpServletResponse.getWriter().close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        } else {
            try {
                super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
