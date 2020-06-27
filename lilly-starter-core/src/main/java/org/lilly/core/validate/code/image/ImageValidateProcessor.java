package org.lilly.core.validate.code.image;

import java.io.IOException;
import javax.imageio.ImageIO;

import org.lilly.core.validate.code.AbstractValidateCodeProcessorImpl;
import org.lilly.core.validate.code.sms.ValidateCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * User: Mr.Wang
 * Date: 2020/6/21
 */
@Component
public class ImageValidateProcessor extends AbstractValidateCodeProcessorImpl {


    @Override
    protected void send(ValidateCode validateCode, ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode imageCode = (ImageCode) validateCode;
        //3.以输出流显示到页面
        try {
            ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (request.getResponse().getOutputStream() != null) {
                    request.getResponse().getOutputStream().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
