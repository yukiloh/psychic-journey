package xyz.murasakichigo.community.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import xyz.murasakichigo.community.dto.AccessTokenDTO;
import xyz.murasakichigo.community.dto.CommunityUser;
import xyz.murasakichigo.community.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.murasakichigo.community.mapper.IUserMapper;
import xyz.murasakichigo.community.provider.GithubProvider;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/*授权登陆的控制器*/
@Controller
public class AuthorizeController {

    @Autowired
    /*提供一个由spring容器管理的provider,用于注入dto&token*/
    private GithubProvider githubProvider;

    @Autowired
    private AccessTokenDTO accessTokenDTO;

    @Autowired
    private IUserMapper userMapper;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,          /*需要交换访问令牌的临时用户*/
                           @RequestParam(name = "state") String state,      /*本地服务器生成的匹配验证码*/
                           HttpServletRequest request){

        /*封装获取的accessToken的数据*/
//        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(accessTokenDTO.getRedirect_uri());
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(accessTokenDTO.getClient_id());
        accessTokenDTO.setClient_secret(accessTokenDTO.getClient_secret());

        /*传入dto,获取accessToken*/
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);/*C + A + v: 快速生成变量*/
        /*传入token,获取user*/
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user);

        /*项目概述:用于验证登陆github账户,可以获取GithubUser中的所有信息(本案例获取了login(用户名) id 更新时间)
         * 步骤:
         * 1.参照github提供的api,需要本地服务器提供连接,内容包含:
         *   client_id:传入服务器id   redirect_uri= 获取返回的登陆页面 scope:获取user信息  state: 生成随机字符串,防止跨站攻击
         *   github服务器会返回code(用于登陆的临时码)和state
         * 2.将client_id   client_secret   redirect_uri   code   state 5项数据再次传至git(post方法,https://github.com/login/oauth/access_token)
         *   会返回(响应)此格式的数据:access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer
         * 3.解析其中的access_token(可以使用split),再次请求git(get方法,https://api.github.com/user?access_token=)
         *   返回完整的user的信息        */


        /*判断是否获取到了user*/
        if (user != null) {
            System.out.println("user is not empty!");
            /*错误点:request.getSession后才能再setAttribute！*/
            request.getSession().setAttribute("user",user);

            /*存入本地数据库*/
            String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());/*获取当前时间的timestamp*/
            CommunityUser communityUser = new CommunityUser();
            communityUser.setGithub_account_id(Long.toString(user.getId()));
            communityUser.setUsername(user.getLogin());
            if (communityUser.getGmt_create() == null) {communityUser.setGmt_create(datetime);}
            communityUser.setGmt_modified(datetime);
            System.out.println(communityUser);

            userMapper.createUser(communityUser);






            /*无论成功(携带session)与否重定向 至/login*/
            return "redirect:/login";
        }else return "redirect:/login";
    }

}
