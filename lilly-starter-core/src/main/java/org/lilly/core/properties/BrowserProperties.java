package org.lilly.core.properties;


/**
 * User: Mr.Wang
 * Date: 2020/6/7
 */
public class BrowserProperties {
    /**
     * 默认登录页地址
     */
    private String loginPage = "/standard-login";
    /**
     * 默认返回json
     */
    private LoginType loginType = LoginType.JSON;

    /**
     * 默认记住我的时间
     */
    private int rememberMeSeconds = 3600;

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
