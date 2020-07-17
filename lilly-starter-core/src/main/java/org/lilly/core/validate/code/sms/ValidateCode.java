package org.lilly.core.validate.code.sms;

import java.time.LocalDateTime;

/**
 * User: Mr.Wang
 * Date: 2020/6/14
 * 手机验证码类
 */
public class ValidateCode {
    /**
     * 随机数验证码
     */
    private String code;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;


    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;

    }

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isExpire() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
