package xyz.murasakichigo.community.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*授权登陆的控制器*/
public class AuthorizeController {

    /*返回callback*/
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,  /*git返回的,需要交换访问令牌的临时用户*/
                           @RequestParam(name = "state") String state /*用于接受state,匹配验证*/){
        return "login";
    }
}
