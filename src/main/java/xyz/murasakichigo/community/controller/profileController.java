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
    @Autowired
    private IUserMapper userMapper;

    /*个人问题页面*/
    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        CommunityUser communityUser = checkCookie(request);

        if (communityUser != null) {
            String page = request.getParameter("page");
            if (page == null || page.length() == 0) {
                page = "1";
            }
            int pageNumber = Integer.valueOf(page);
            Integer id = communityUser.getId();
            showPage(request,id,pageNumber);
        }
        return "question";
    }


    private void showPage(HttpServletRequest request,int id,int pageNumber) {
        List<CommunityQuestion> questions = questionMapper.findQuestionById(id,(pageNumber-1)*10);
        Integer questionCount = questionMapper.countProfileQuestion(id);
        Integer maxPage = (questionCount/10)+1;

        request.getSession().setAttribute("questions",questions);
        request.getSession().setAttribute("maxPage",maxPage);
        request.getSession().setAttribute("page",pageNumber);

    }


    private CommunityUser checkCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie c : cookies) {
                String token = c.getName();
                if ("token".equals(token)) {
                    CommunityUser communityUser = userMapper.findUserByToken(c.getValue());
                    return communityUser;
                }
            }
        }
        return new CommunityUser();
    }


}
