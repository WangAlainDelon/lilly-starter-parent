package org.lilly.core.social.qq.conditionconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;

/**
 * Created by wangxiang on 2020/8/9
 */
@Configuration
@ConditionalOnProperty(prefix = "lilly.security.social.qq",name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        super.addConnectionFactories(connectionFactoryConfigurer, environment);
    }
}
