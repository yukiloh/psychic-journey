package xyz.murasakichigo.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xyz.murasakichigo.community.dto.CommunityUser;
import xyz.murasakichigo.community.mapper.IUserMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/*使用thymeleaf解析一个欢迎页面*/
/*记得路径必须与启动类相同！！*/  /*不可用@RestController，会自动添加@ResponseBody导致页面解析错误*/
@Controller
public class MainController {


    /*springboot提供的标准greeting演示*/
    @GetMapping("/greeting")        /*@GetMapping = @RequestMapping(method = RequestMethod.GET)*/
    /*@RequestParam中,required:可以不进行传参,并且defaultValue指定为World*/
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";  /*返回一个greeting.html页面,会默认从resources/templates/下查找; 方法上不可添加@ResponseBody!!*/
    }


    /*================================================================================================================*/
    /*设置初始访问页,跳转至index*/
    @GetMapping("/")
    public String index() {
        return "index";
    }


    @Autowired
    private IUserMapper userMapper;

    /*登陆页面（应该命名为homepage）；会判断是否携带token，是则直接调用数据库；应该交由拦截器*/
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        /*判断是否携带token的cookie,如果有就判断是否匹配数据库的token,一致则登陆*/
        Cookie[] cookies = request.getCookies();
        /*如果存在cookies,遍历获取名为token的cookie,并通过此cookie查询sql获取user信息*/
        if (cookies != null) {
            for (Cookie c : cookies) {
                String token = c.getName();
                if ("token".equals(token)) {
                    CommunityUser communityUser = userMapper.findUserByToken(c.getValue());
                    if (communityUser != null) {
                        /*与request.setAttribute不同,r.g.s可以在多个页面、重定向后保留session*/
                        request.getSession().setAttribute("communityUser", communityUser);  /*所以返回前端的是communityUser*/
                    } else {
                        System.out.println("not find c_user,by token");
                    }
                    break;  /*找到则停止循环*/
                }
            }
            /*或者可以在这里加入其他的验证信息*/

            return "login";
        } else return "login";
    }




}
