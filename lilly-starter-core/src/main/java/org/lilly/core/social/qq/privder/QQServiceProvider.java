package org.lilly.core.social.qq.privder;

import org.lilly.core.social.qq.api.QQ;
import org.lilly.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;


/**
 * Created by wangxiang on 2020/7/21
 * QQ 服务提供商的实现类
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    /**
     * Create a new {@link org.springframework.social.oauth2.OAuth2ServiceProvider}.
     *
     * @param oauth2Operations the OAuth2Operations template for conducting the OAuth 2 flow with the provider.
     */
    public QQServiceProvider(OAuth2Operations oauth2Operations) {
        //new OAuth2Template();
        super(oauth2Operations);
    }


    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, this.appId);
    }
}
