package org.lilly.core.social.qq.oauth2;

import java.nio.charset.Charset;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by wangxiang on 2020/9/1
 */
public class QQOAuth2Template extends OAuth2Template {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        //手动设置为true，装填参数，并且是在getOAuthOperations().exchangeForAccess()的exchangeForAccess方法调用之前设置
        super.setUseParametersForClientAuthentication(true);
    }



    /**
     * 重写createRestTemplate方法
     *
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    /**
     * 对返回信息的封装,qq返回的是String 不是Map
     *
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        logger.info("返回的accessToken为：{}", responseStr);
        //拿到的返回为 access_token=BB38C1E5D2F2E76978F49CF0CFF633A7&expires_in=7776000&refresh_token=364AD251C1FC1866A7E21CCE2893299A
        String[] responseArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
        AccessGrant accessGrant = new AccessGrant(StringUtils.substringAfterLast(responseArray[0], "="),
                null, StringUtils.substringAfterLast(responseArray[2], "="),
                Long.valueOf(StringUtils.substringAfterLast(responseArray[1], "=")));
        return accessGrant;
    }
}
