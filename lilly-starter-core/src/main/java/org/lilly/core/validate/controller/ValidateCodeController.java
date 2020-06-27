package org.lilly.core.validate.controller;

import org.apache.commons.lang.StringUtils;
import org.lilly.core.validate.code.ValidateCodeProcessor;
import org.lilly.core.validate.code.image.ImageCode;
import org.lilly.core.validate.code.generator.ValidateGenerator;
import org.lilly.core.validate.code.sms.ValidateCode;
import org.lilly.core.validate.code.sms.sender.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * User: Mr.Wang
 * Date: 2020/6/9
 * 生成图形验证码的接口
 */
@RestController
public class ValidateCodeController {
//    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
//    private static final String SESSION_KEY = "SESSION_CODE";
//
//    @Autowired
//    private ValidateGenerator imageValidateGenerator;
//
//    @Autowired
//    private ValidateGenerator smsValidateGenerator;
//
//    @Autowired
//    private SmsCodeSender smsCodeSender;

    @Autowired
    private Map<String, ValidateCodeProcessor> codeProcessorMap;

    @GetMapping("/code/{type}")
    public void createCode(@PathVariable String type,
                           HttpServletRequest httpRequest,
                           HttpServletResponse httpResponse) throws ServletRequestBindingException {
        for (Map.Entry<String, ValidateCodeProcessor> stringValidateCodeProcessorEntry : codeProcessorMap.entrySet()) {

            System.out.println(stringValidateCodeProcessorEntry.getKey());

        }
        codeProcessorMap.get(type + "ValidateProcessor")
                .create(new ServletWebRequest(httpRequest, httpResponse));
    }


//    /**
//     * 生成图形验证码接口
//     *
//     * @param httpRequest
//     * @param httpResponse
//     */
//    @GetMapping("/code/image")
//
//    public void createImageCode(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
//        //1.生成图形验证码
//        ImageCode imageCode = (ImageCode) imageValidateGenerator.generator();
//        //2.将图形验证码存入session中
//        sessionStrategy.setAttribute(new ServletWebRequest(httpRequest), SESSION_KEY, imageCode);
//        //3.以输出流显示到页面
//        try {
//            ImageIO.write(imageCode.getImage(), "JPEG", httpResponse.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (httpResponse.getOutputStream() != null) {
//                    httpResponse.getOutputStream().close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 短信验证码接口
//     *
//     * @param request
//     * @param response
//     * @throws ServletRequestBindingException
//     */
//    @GetMapping("/sms/code")
//    public void createSmsCode(HttpServletRequest request, HttpServletResponse response)
//            throws ServletRequestBindingException {
//        //1.生成短信验证码
//        ValidateCode smsCode = smsValidateGenerator.generator();
//        //2.存入session
//        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
//        //3.调用短信服务商发送短信
//        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
//        smsCodeSender.send(mobile, smsCode.getCode());
//    }

}
