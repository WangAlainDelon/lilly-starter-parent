package org.lilly.core.validate.code.image;

import java.io.IOException;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.lilly.core.SecurityCoreConfig;
import org.lilly.core.properties.SecurityProperties;
import org.lilly.core.utils.SecurityConstant;
import org.lilly.core.validate.code.AbstractValidateCodeProcessorImpl;
import org.lilly.core.validate.code.ValidateException;
import org.lilly.core.validate.code.sms.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * User: Mr.Wang
 * Date: 2020/6/21
 */
@Component
public class ImageValidateProcessor extends AbstractValidateCodeProcessorImpl {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void send(ValidateCode validateCode, ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode imageCode = (ImageCode) validateCode;
        //3.以输出流显示到页面
        try {
            ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (request.getResponse().getOutputStream() != null) {
                    request.getResponse().getOutputStream().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 校验验证码(包括图形以及短信验证码)
     */
    @Override
    public void validate(ServletWebRequest servletWebRequest) {
        String key = SecurityConstant.SESSION_KEY + "IMAGE";
        ImageCode sessionImageCode = (ImageCode) sessionStrategy.getAttribute(servletWebRequest, key);
        String imageCode = servletWebRequest.getRequest().getParameter("imageCode");
        if (StringUtils.isBlank(imageCode)) {
            throw new ValidateException("验证码为空");
        }
        if (sessionImageCode == null) {
            throw new ValidateException("验证码不存在");
        }
        if (sessionImageCode.isExpire()) {
            throw new ValidateException("验证码过期");
        }
        if (!StringUtils.equalsIgnoreCase(imageCode, sessionImageCode.getCode())) {
            throw new ValidateException("验证码不匹配");
        }
    }
}
