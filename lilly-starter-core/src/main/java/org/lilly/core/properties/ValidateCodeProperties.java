package org.lilly.core.properties;


import org.lilly.core.properties.image.ImageCodePropertites;
import org.lilly.core.properties.sms.SmsCodePropertites;

/**
 * User: Mr.Wang
 * Date: 2020/6/11
 * 验证码配置类
 */
public class ValidateCodeProperties {
    private ImageCodePropertites imageCode = new ImageCodePropertites();

    private SmsCodePropertites smsCode = new SmsCodePropertites();

    public ImageCodePropertites getImageCode() {
        return imageCode;
    }

    public void setImageCode(ImageCodePropertites imageCode) {
        this.imageCode = imageCode;
    }

    public SmsCodePropertites getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(SmsCodePropertites smsCode) {
        this.smsCode = smsCode;
    }
}
