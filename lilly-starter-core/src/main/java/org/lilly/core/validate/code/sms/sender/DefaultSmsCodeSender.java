package org.lilly.core.validate.code.sms.sender;

/**
 * User: Mr.Wang
 * Date: 2020/6/21
 * 默认短信发送类(用户自定义实现的发送类可以覆盖此默认类)
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机号" + mobile + "的手机发送短信验证码为：" + code);
    }
}
