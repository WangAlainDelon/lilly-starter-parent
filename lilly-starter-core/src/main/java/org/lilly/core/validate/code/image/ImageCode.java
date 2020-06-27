package org.lilly.core.validate.code.image;

import org.lilly.core.validate.code.sms.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * User: Mr.Wang
 * Date: 2020/6/9
 * 图片验证码包装类
 */
public class ImageCode extends ValidateCode {
    /**
     * 验证码图片
     */
    private BufferedImage image;


    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;

    }


    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;

    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
