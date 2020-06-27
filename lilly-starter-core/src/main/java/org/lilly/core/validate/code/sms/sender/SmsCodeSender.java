package org.lilly.core.validate.code.sms.sender;

/**
 * User: Mr.Wang
 * Date: 2020/6/21
 * 短信验证码发送接口
 */
public interface SmsCodeSender {
    void send(String mobile, String code);
}
