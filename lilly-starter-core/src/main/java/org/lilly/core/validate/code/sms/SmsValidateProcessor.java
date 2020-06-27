package org.lilly.core.validate.code.sms;

import org.lilly.core.validate.code.AbstractValidateCodeProcessorImpl;
import org.lilly.core.validate.code.sms.sender.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    protected void send(ValidateCode validateCode, ServletWebRequest request) throws ServletRequestBindingException {
        //3.调用短信服务商发送短信
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}
