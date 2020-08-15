package org.lilly.core.social.qq.connection;

import org.lilly.core.social.qq.api.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * Created by wangxiang on 2020/7/21
 */
public class QQConnection extends OAuth2Connection<QQ> {
    public QQConnection(String providerId, String providerUserId, String accessToken, String refreshToken, Long expireTime, OAuth2ServiceProvider<QQ> serviceProvider, ApiAdapter<QQ> apiAdapter) {
        super(providerId, providerUserId, accessToken, refreshToken, expireTime, serviceProvider, apiAdapter);
    }

    public QQConnection(ConnectionData data, OAuth2ServiceProvider<QQ> serviceProvider, ApiAdapter<QQ> apiAdapter) {
        super(data, serviceProvider, apiAdapter);
    }
}
