package xyz.murasakichigo.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.murasakichigo.community.dto.CommunityQuestion;
import xyz.murasakichigo.community.mapper.IQuestionMapper;
import xyz.murasakichigo.community.mapper.IUserMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    private IQuestionMapper questionMapper;

    /*主页（应该命名为homepage）；会判断是否携带token，是则直接调用数据库；应该交由拦截器*/
    @GetMapping("/homepage")
    public String homepage(HttpServletRequest request) {
        /*展示页面内容*/
        String page = request.getParameter("page");
        if (page == null || page.length() == 0) {
            page = "1";
        }
        showPage(request,page);
        return "homepage";
    }

    /*登陆页面*/
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /*登出*/
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        /*删除cookie*/
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        /*清除session*/
        request.getSession().removeAttribute("communityUser");
//        request.getSession().invalidate();      /*可能会出现连续第三次登陆无法登陆的情况*/
        return "redirect:/homepage";
    }



//    ================================================================================================================
    /*返回前端基于当前页面数的列表*/
    private void showPage(HttpServletRequest request,String page){
        Integer questionCount = questionMapper.countQuestion();
        Integer maxPage = (questionCount/10)+1 ;

        if(Integer.valueOf(page) > maxPage) {
            page = maxPage.toString();
        }
        else if (Integer.valueOf(page) < 1){
            page = "1";
        }
        int thisPage = Integer.valueOf(page).intValue();

        int i = (thisPage-1)*10;
        List<CommunityQuestion> questionList = questionMapper.findQuestionByPage(i);

        request.getSession().setAttribute("questionList",questionList);
        request.getSession().setAttribute("page",thisPage);
        request.getSession().setAttribute("maxPage",maxPage);
    }
}
