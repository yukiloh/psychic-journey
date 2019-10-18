package xyz.murasakichigo.community.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.murasakichigo.community.dto.CommunityQuestion;
import xyz.murasakichigo.community.dto.CommunityUser;
import xyz.murasakichigo.community.dto.VerificationQuestion;
import xyz.murasakichigo.community.mapper.IQuestionMapper;
import xyz.murasakichigo.community.mapper.IUserMapper;
import xyz.murasakichigo.community.mapper.IVerificationQuestionMapper;
import xyz.murasakichigo.community.utils.CountPaging;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

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


    @Autowired
    private IVerificationQuestionMapper verificationQuestionMapper;
    /*登陆页面*/
    @GetMapping("/login")
    public String login(Model model) {
        Integer integer = verificationQuestionMapper.countVerificationQuestion();
        int id = new Random().nextInt(integer + 1);
        VerificationQuestion question = verificationQuestionMapper.findVerificationQuestionById(id);
        model.addAttribute("question",question.getQuestion());
        model.addAttribute("answer",question.getAnswer());



        return "login";
    }



    @Autowired
    IUserMapper userMapper;
    /*验证登陆信息*/
    @PostMapping("/login.do")
    public String toLogin(String username,String password,Model model,HttpServletRequest request) {
        /*subject可以理解为"对象",抽象概念,会与系统进行交互*/
        Subject subject = SecurityUtils.getSubject();

        /*根据传入的name和password封装为一个subject*/
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        /*执行登陆方法,通过try catch的方法进行判断*/
        try {
            /*一旦执行login必定会执行realm中的认证操作doGetAuthenticationInfo*/
            subject.login(token);

            /*获取当前user*/
            CommunityUser user = (CommunityUser) SecurityUtils.getSubject().getPrincipal();
            request.getSession().setAttribute("communityUser",user);    /*存user*/
            return "redirect:/homepage";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }


    /*登出*/
    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
//            model.addAttribute("msg","安全退出！");
        return "redirect:/login";

        /*原logout代码，已弃用*/
//        /*删除cookie*/
//        Cookie cookie = new Cookie("token",null);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//        /*清除session*/
//        request.getSession().removeAttribute("communityUser");
////        request.getSession().invalidate();      /*可能会出现连续第三次登陆无法登陆的情况*/
//        return "redirect:/homepage";
    }


    /*git登陆失败*/
    @GetMapping("/loginFailed")
    public String loginFailed(){
        return "redirect:/loginFailed";
    }


//    ================================================================================================================
    /*返回前端基于当前页面数的列表*/
    private void showPage(HttpServletRequest request,String page){
        Integer questionCount = questionMapper.countQuestion();
        int[] result = new CountPaging().countPaging(questionCount, page);
        List<CommunityQuestion> questionList = questionMapper.findQuestionByPage(result[2]);
        request.getSession().setAttribute("questionList",questionList);
        request.getSession().setAttribute("page",result[1]);
        request.getSession().setAttribute("maxPage",result[0]);
    }
}
