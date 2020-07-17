package org.lilly.core.validate.code.sms;

import org.apache.commons.lang.StringUtils;
import org.lilly.core.properties.SecurityProperties;
import org.lilly.core.utils.SecurityConstant;
import org.lilly.core.validate.code.AbstractValidateCodeProcessorImpl;
import org.lilly.core.validate.code.ValidateException;
import org.lilly.core.validate.code.sms.sender.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * User: Mr.Wang
 * Date: 2020/6/21
 */
@Component
public class SmsValidateProcessor extends AbstractValidateCodeProcessorImpl {
    @Autowired
    private SmsCodeSender smsCodeSender;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    protected void send(ValidateCode validateCode, ServletWebRequest request) throws ServletRequestBindingException {
        //3.调用短信服务商发送短信
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        smsCodeSender.send(mobile, validateCode.getCode());
    }

    /**
     * 校验验证码(包括图形以及短信验证码)
     */
    @Override
    public void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        //请求中传来的验证码
        String smsCode = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "smsCode");
        // session key
        String key = SecurityConstant.SESSION_KEY + "sms";
        ValidateCode sessionValidateCode = (ValidateCode) sessionStrategy.getAttribute(servletWebRequest, key);
        if (StringUtils.isBlank(smsCode)) {
            throw new ValidateException("验证码为空");
        }
        if (sessionValidateCode == null) {
            throw new ValidateException("验证码不存在");
        }
        if (sessionValidateCode.isExpire()) {
            throw new ValidateException("验证码已过期");
        }
        if (StringUtils.equalsIgnoreCase(sessionValidateCode.getCode(), smsCode)) {
            throw new ValidateException("验证码已过期");
        }

    }
}
