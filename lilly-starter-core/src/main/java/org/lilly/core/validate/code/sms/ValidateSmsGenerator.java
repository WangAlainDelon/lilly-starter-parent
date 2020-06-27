package org.lilly.core.validate.code.sms;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.lilly.core.properties.SecurityProperties;
import org.lilly.core.validate.code.generator.ValidateGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Mr.Wang
 * Date: 2020/6/14
 * 短信验证码生成器
 */
@Component("smsValidateGenerator")
public class ValidateSmsGenerator implements ValidateGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generator() {
        String smsCode = RandomStringUtils.randomNumeric(securityProperties.getValidateCode().getSmsCode().getLength());
        return new ValidateCode(smsCode,
                securityProperties.getValidateCode().getSmsCode().getExpireTime());
    }
}
