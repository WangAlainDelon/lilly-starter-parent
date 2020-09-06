package org.lilly.core.social.qq.connection;

import org.lilly.core.social.qq.api.QQ;
import org.lilly.core.social.qq.privder.QQServiceProvider;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Created by wangxiang on 2020/7/21
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    private String clientId;
    private String clientSecret;

    public QQConnectionFactory(String providerId, String clientId, String clientSecret) {
        super(providerId, new QQServiceProvider(clientId, clientSecret), new QQAdapter());
    }
}
