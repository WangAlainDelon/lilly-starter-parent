package org.lilly.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.lilly.core.social.qq.UserInfo;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * Created by Andy on 2020/7/20
 * 调用get_user_info获取用户的信息
 * 这个角色就是ServiceProvider里面的API接口的实现
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    /**
     * 申请QQ登录成功后，分配给应用的appid，
     */
    private String appId;
    /**
     * 用户的ID，与QQ号码一一对应。
     * 可通过调用https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN 来获取。
     */
    private String openId;

    /**
     * 获得openId的URL
     * <p>
     * 返回：callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
     */
    private String URL_GET_OPERNID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * 获得用户信息的URL
     * https://graph.qq.com/user/get_user_info?access_token=YOUR_ACCESS_TOKEN&oauth_consumer_key=YOUR_APP_ID&openid=YOUR_OPENID
     * access_token已经在AbstractOAuth2ApiBinding生成RestTemplate的时候绑定了，他可以设置请求体的转换器和请求的拦截器
     */
    private String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private ObjectMapper objectMapper = new ObjectMapper();


    public QQImpl(String accessToken, String appId) {
        //Token的策略TokenStrategy，将参数放在url中
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPERNID, accessToken);
        String result = super.getRestTemplate().getForEntity(url, String.class).getBody();
        String openId = StringUtils.substringBetween(result, "openid\":\"", "\"}");
        this.openId = openId;
    }

    @Override
    public UserInfo getUserInfo() {
        String url = String.format(URL_GET_USERINFO, this.appId, this.openId);
        String object = super.getRestTemplate().getForObject(url, String.class);
        UserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(object, UserInfo.class);
            userInfo.setOpenId(this.openId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
