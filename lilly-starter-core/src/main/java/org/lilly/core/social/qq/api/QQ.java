package org.lilly.core.social.qq.api;

import org.lilly.core.social.qq.UserInfo;

/**
 * 服务提供商中Api接口的抽象
 * 这个接口就只有一个功能获取用户信息
 */
public interface QQ {

    UserInfo getUserInfo();
}
