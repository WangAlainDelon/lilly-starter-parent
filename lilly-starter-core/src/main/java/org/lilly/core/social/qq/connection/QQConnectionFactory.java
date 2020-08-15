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
    /**
     * 将用户重定向到认证服务器的地址
     */
    private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";


    /**
     * 获取访问令牌的URL
     */
    private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";

    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId      the provider id e.g. "facebook"
     * @param serviceProvider the ServiceProvider model for conducting the authorization flow and obtaining a native service API instance.
     * @param apiAdapter      the ApiAdapter for mapping the provider-specific service API model to the uniform {@link Connection} interface.
     */
    public QQConnectionFactory(String providerId, OAuth2ServiceProvider<QQ> serviceProvider, ApiAdapter<QQ> apiAdapter,
                               String clientId, String clientSecret) {
        super(providerId,
                new QQServiceProvider(new OAuth2Template(clientId, clientSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL)),
                new QQAdapter());
    }
}
