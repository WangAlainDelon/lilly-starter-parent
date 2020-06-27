package org.lilly.core.properties.image;

import org.lilly.core.properties.sms.SmsCodePropertites;

/**
 * User: Mr.Wang
 * Date: 2020/6/11
 * 图形验证码的配置类和他的默认配置
 */
public class ImageCodePropertites extends SmsCodePropertites {

    public ImageCodePropertites() {
        setLength(4);
    }

    private int width = 67;
    private int height = 23;


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


}
