package org.lilly.core.validate.config;

import org.lilly.core.properties.SecurityProperties;
import org.lilly.core.validate.code.generator.ValidateGenerator;
import org.lilly.core.validate.code.image.ValidateImageGenerator;
import org.lilly.core.validate.code.sms.sender.DefaultSmsCodeSender;
import org.lilly.core.validate.code.sms.sender.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: Mr.Wang
 * Date: 2020/6/13
 */
@Configuration
public class ValidateCodeBeanConfig {
    @Autowired
    private SecurityProperties securityProperties;


    /**
     * 当Spring容器中没有名字为validateImageGenerator的bean时，才走下面的这段配置
     * 注入容器的bean的名字为imageValidateGenerator
     * @return ValidateGenerator
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateGenerator")
    public ValidateGenerator imageValidateGenerator() {
        ValidateImageGenerator validateImageGenerator = new ValidateImageGenerator(securityProperties);
        return validateImageGenerator;
    }

    /**
     * 当容器中不存在SmsCodeSender接口的实现的时候，使用以下默认的配置
     *
     * @return SmsCodeSender
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        SmsCodeSender defaultSmsCodeSender = new DefaultSmsCodeSender();
        return defaultSmsCodeSender;
    }
}
