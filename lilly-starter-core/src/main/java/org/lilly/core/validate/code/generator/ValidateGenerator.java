package org.lilly.core.validate.code.generator;

import org.lilly.core.validate.code.sms.ValidateCode;

/**
 * User: Mr.Wang
 * Date: 2020/6/13
 * 验证码生成接口(T)
 */
public interface ValidateGenerator {

    ValidateCode generator();
}
