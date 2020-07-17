package org.lilly.core.validate.code;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * User: Mr.Wang
 * Date: 2020/6/21
 * 验证码的生成接口
 * 验证码校验接口
 */
public interface ValidateCodeProcessor {
    /**
     * 验证码处理的方法，传入请求和响应
     *
     * @param request
     */
    void create(ServletWebRequest request) throws ServletRequestBindingException;

    /**
     * 校验验证码的（包括图形和SMS）
     * @param request
     */
    void validate(ServletWebRequest request) throws ServletRequestBindingException;

}
