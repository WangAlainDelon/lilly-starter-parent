package org.lilly.browser;

import org.springframework.context.annotation.Configuration;

/**
 * User: Mr.Wang
 * Date: 2020/5/30
 */
@Configuration
//@ConditionalOnClass(BrowserDemo.class)  //应用中包含某个类时对应的配置才生效
//@EnableConfigurationProperties(BrowserProperties.class)
public class BrowserAutoConfiguration {
//    @Bean
//    @ConditionalOnMissingBean  //Spring容器中不存在指定Class的实例对象时，对应的配置才生效
//    public BrowserDemo browserDemo(BrowserProperties browserProperties) {
//        BrowserDemo browserDemo = new BrowserDemo();
//        browserDemo.setLoginPage(browserProperties.getLoginPage());
//        return browserDemo;
//    }
}
