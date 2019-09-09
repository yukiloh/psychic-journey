package xyz.murasakichigo.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.murasakichigo.community.dto.CommunityQuestion;
import xyz.murasakichigo.community.dto.CommunityUser;
import xyz.murasakichigo.community.mapper.IQuestionMapper;
import xyz.murasakichigo.community.mapper.IUserMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class profileController {

    @Autowired
    private IQuestionMapper questionMapper;


    /*个人问题页面，如果没有获取到cUser则返回登陆页面*/
    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        CommunityUser communityUser = (CommunityUser) request.getSession().getAttribute("communityUser");
        if (communityUser != null) {
            /*如果获取到commUser则获取page页面*/
            String page = request.getParameter("page");
            if (page == null || page.length() == 0) {
                page = "1"; /*默认赋予page = 1*/
            }
            int pageNumber = Integer.valueOf(page);
            /*通过id获取个人页面的帖子列表*/
            Integer id = communityUser.getId();
            /*通过调用域显示页面*/
            showPage(request,id,pageNumber);
            return "question";
        }else return "redirect:/login";      /*如果没有获取到cUser则重定向至登陆页面*/
    }


    private void showPage(HttpServletRequest request,int id,int pageNumber) {
        List<CommunityQuestion> questions = questionMapper.findQuestionById(id,(pageNumber-1)*10);
        Integer questionCount = questionMapper.countProfileQuestion(id);
        Integer maxPage = (questionCount/10)+1;

        request.getSession().setAttribute("questions",questions);
        request.getSession().setAttribute("maxPage",maxPage);
        request.getSession().setAttribute("page",pageNumber);

    }



}
