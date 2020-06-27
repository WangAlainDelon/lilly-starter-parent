package org.lilly.browser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: Mr.Wang
 * Date: 2020/6/7
 */
@Controller
public class BrowserSecurityController {

    /**
     * 第一版登录请求的地址
     * @param error
     * @return
     */
//    @GetMapping("login")
//    public ModelAndView login(String error) {
//        ModelAndView modelAndView = new ModelAndView("login");
//        modelAndView.addObject("error", error);
//        return modelAndView;
//    }

    /**
     * 登陆成功跳转这里
     */
    @PostMapping("/hello")
    public String test() {
        System.out.println("hello security");
        return "index";
    }
}
