package org.lilly.core.properties.social;

/**
 * Created by wangxiang on 2020/8/15
 */
public class SocialProperties {

    private String filterProcessesUrl = "/login";

    private QQProperties qq = new QQProperties();

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }
}
