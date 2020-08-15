package org.lilly.core.utils;

public interface SecurityConstant {

    /**
     * 表单登录的请求地址
     */
    String AUTHENTICATION_FORM_LOGIN = "/authentication/form";

    /**
     * 短信验证登录的请求地址
     */
    String AUTHENTICATION_MOBILE_LOGIN = "/authentication/mobile";

    /**
     * 验证码的SessionKey前缀
     */
    String SESSION_KEY = "SESSION_CODE_";
    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";
}
