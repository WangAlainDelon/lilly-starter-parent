package org.lilly.core.social.qq.connection;

import org.lilly.core.social.qq.UserInfo;
import org.lilly.core.social.qq.api.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * Created by wangxiang on 2020/7/21
 * 将从服务提供商那里获取到的用户信息转换为标准的用户信息的桥梁
 */
public class QQAdapter implements ApiAdapter<QQ> {
    /**
     * 测试qq是否能链接上
     * 这里默认QQ连接通过
     *
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        UserInfo userInfo = api.getUserInfo();
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);
        // 用户在服务提供商那边的唯一标识
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {

    }
}
