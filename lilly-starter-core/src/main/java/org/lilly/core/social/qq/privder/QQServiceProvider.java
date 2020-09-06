package org.lilly.core.social.qq.privder;

import org.lilly.core.social.qq.api.QQ;
import org.lilly.core.social.qq.api.QQImpl;
import org.lilly.core.social.qq.oauth2.QQOAuth2Template;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;


/**
 * Created by wangxiang on 2020/7/21
 * QQ 服务提供商的实现类
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    /**
     * 将用户导向授权服务器的地址
     */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    /**
     * 第四步中获取access_token的地址
     */
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";


    public QQServiceProvider(String clientId, String clientSecret) {
        super(new QQOAuth2Template(clientId, clientSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = clientId;
    }


    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, this.appId);
    }
}
