package org.lilly.core.properties.social;

import org.lilly.core.social.defect.SocialProperties;

/**
 * Created by wangxiang on 2020/8/15
 */
public class QQProperties extends SocialProperties {

    /**
     * 默认是qq
     */
    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
