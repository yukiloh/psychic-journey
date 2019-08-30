package xyz.murasakichigo.community.controller;

import dto.AccessTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.murasakichigo.community.provider.GithubProvider;

/*授权登陆的控制器*/
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    /*返回callback*/
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,  /*git返回的,需要交换访问令牌的临时用户*/
                           @RequestParam(name = "state") String state /*用于接受state,匹配验证*/){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("Iv1.2299807260a02e0e");
        accessTokenDTO.setClient_secret("b0500db3906303de1ab10cca68da8e33b7ad1b64");
        githubProvider.getAccessToken(accessTokenDTO);  /*C + A + v: 快速生成变量*/
        return "login";
    }
}
