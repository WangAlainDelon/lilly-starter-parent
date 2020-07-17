package org.lilly.core.utils;

public interface SecurityConstant {

    /**
     * 表单登录的请求地址
     */
    String AUTHENTICATION_FORM = "/authentication/form";

    /**
     * 短信验证登录的请求地址
     */
    String AUTHENTICATION_MOBILE = "/authentication/mobile";

    /**
     * 验证码的SessionKey前缀
     */
    String SESSION_KEY = "SESSION_CODE_";
}
