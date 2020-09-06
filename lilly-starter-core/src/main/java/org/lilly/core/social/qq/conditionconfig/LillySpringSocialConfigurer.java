package org.lilly.core.social.qq.conditionconfig;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Created by wangxiang on 2020/8/15
 * 解决qq登录的路径问题 将源码中的/auth/ 替换成/login
 */
public class LillySpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public LillySpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) filter;
    }
}

