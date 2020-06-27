package org.lilly.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * User: Mr.Wang
 * Date: 2020/6/9
 */
public class ValidateException extends AuthenticationException {
    public ValidateException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateException(String msg) {
        super(msg);
    }
}
