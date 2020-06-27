package org.lilly.core.properties.sms;

/**
 * User: Mr.Wang
 * Date: 2020/6/14
 */
public class SmsCodePropertites {
    private int length = 6;
    private int expireTime = 60;
    private String url;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
