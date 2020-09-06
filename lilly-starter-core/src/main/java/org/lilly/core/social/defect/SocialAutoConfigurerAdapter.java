package org.lilly.core.social.defect;

import org.omg.CORBA.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;

/**
 * Created by wangxiang on 2020/8/15
 *
 * 2.x源码缺失 手动重写
 */
public abstract class SocialAutoConfigurerAdapter extends SocialConfigurerAdapter {
    public SocialAutoConfigurerAdapter() {
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, org.springframework.core.env.Environment environment) {
        connectionFactoryConfigurer.addConnectionFactory(this.createConnectionFactory());
    }

    protected abstract ConnectionFactory<?> createConnectionFactory();
}

