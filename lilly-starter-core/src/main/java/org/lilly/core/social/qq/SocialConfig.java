package org.lilly.core.social.qq;

import javax.sql.DataSource;
import org.lilly.core.properties.SecurityProperties;
import org.lilly.core.social.qq.conditionconfig.LillySpringSocialConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.CollectionFactory;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by wangxiang on 2020/7/21
 * 配置数据库层的配置
 */
@Configuration
@Component
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;


    @Override
    @Bean
    @Primary
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //Encryptors 是connection数据库信息加解密的工具
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository =
                new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        jdbcUsersConnectionRepository.setTablePrefix("lilly_");
        if (connectionSignUp != null) {
            jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
        }
        return jdbcUsersConnectionRepository;
    }

    /**
     * 将SocialAuthenticationFilter加入Spring Security的过滤器链上去
     * 还没有加上去  过滤器链：https://blog.csdn.net/qq_35067322/article/details/102690579
     *
     * @return
     */
    @Bean
    public SpringSocialConfigurer lillySpringSocialConfigurer() {
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        LillySpringSocialConfigurer configurer = new LillySpringSocialConfigurer(filterProcessesUrl);
        //设置注册页面的地址
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        return configurer;
//        return new SpringSocialConfigurer();
    }

    /**
     * Spring Social提供了工具类，解决了在注册过程中如何拿到Spring Social的信息，和注册成功后Spring Social如何拿到业务系统的信息。
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
        return providerSignInUtils;
    }

}
