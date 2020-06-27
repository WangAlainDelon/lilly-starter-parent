package org.lilly.core.validate.code;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.lilly.core.validate.code.generator.ValidateGenerator;
import org.lilly.core.validate.code.sms.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * User: Mr.Wang
 * Date: 2020/6/21
 */
public abstract class AbstractValidateCodeProcessorImpl<C extends ValidateCode> implements ValidateCodeProcessor {
    private static final String SESSION_KEY = "SESSION_CODE_";
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private Map<String, ValidateGenerator> validateGeneratorMap;

    @Override
    public void create(ServletWebRequest request) throws ServletRequestBindingException {
        //1.生成验证码
        ValidateCode validateCode = generator(request);
        //2.保存验证码
        save(validateCode, request);
        //3.发送验证码
        send(validateCode, request);
    }

    /**
     * 不同验证码发送逻辑不同，所以发送逻辑留待子类实现
     *
     * @param validateCode
     * @param request
     * @throws ServletRequestBindingException
     */
    protected abstract void send(ValidateCode validateCode, ServletWebRequest request) throws ServletRequestBindingException;

    private void save(ValidateCode validateCode, ServletWebRequest request) {
        //存入Session
        String key = SESSION_KEY + getRequestType(request).toUpperCase();
        sessionStrategy.setAttribute(request, key, validateCode);
    }

    private ValidateCode generator(ServletWebRequest request) {
        //根据请求的类型来获取不同验证码的生成器,然后生成验证码返回
        String type = getRequestType(request);
        return validateGeneratorMap.get(type + "ValidateGenerator").generator();
    }

    private String getRequestType(ServletWebRequest request) {
        // /code/sms  -> sms
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code");
    }
}
