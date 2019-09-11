package xyz.murasakichigo.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.murasakichigo.community.dto.CommunityQuestion;
import xyz.murasakichigo.community.dto.CommunityUser;
import xyz.murasakichigo.community.mapper.IQuestionMapper;
import xyz.murasakichigo.community.mapper.IUserMapper;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/*用于提交问题的控制器*/
@Controller
public class QuestionController {

    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private IQuestionMapper questionMapper;

    /*进入提交问题页面*/
    @GetMapping("/profile/newIssue")
    public String newIssue() {
        return "newIssue";
    }


//    提交问题页面的submit按钮
    /*当接收到get时原路返回(理由不明)*/
    @GetMapping("/profile/questionSubmit")
    public String getQuestion() {
        return "redirect:/question";
    }

    /*使用post接受*/
    @PostMapping("/profile/questionSubmit")
    public String postQuestion(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "tag") String tag,
            @CookieValue(value = "token")String token)  {

        CommunityQuestion communityQuestion = new CommunityQuestion();
        communityQuestion.setTitle(title);
        communityQuestion.setDescription(description);
        communityQuestion.setTag(tag);
        Integer id = userMapper.findUserByToken(token).getId(); /*获取id*/
        communityQuestion.setAuthor_user_id(id);
        communityQuestion.setAuthor_name(userMapper.findUserById(id).getUsername());    /*通过id查找userName*/
        communityQuestion.setGmt_create(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        communityQuestion.setTag(tag);
        questionMapper.createIssue(communityQuestion);

        /*应该是重定向至成功页面,然后返回到问题浏览的...*/
        return "redirect:/homepage";
    }
//===================================================================
    /*进入问题修改*/
    @GetMapping("/profile/issueEdit/{id}")
    public String issueEdit(@PathVariable String id,
                            HttpServletRequest request) {
        /*验证是否issue作者ID与登陆者相同*/
        CommunityUser communityUser = (CommunityUser) request.getSession().getAttribute("communityUser");
        if (communityUser.getId() == questionMapper.findQuestionByIssueId(id).getAuthor_user_id()) {
            CommunityQuestion questionByIssueId = questionMapper.findQuestionByIssueId(id);
            request.getSession().setAttribute("questionByIssueId",questionByIssueId);
            return "issueEdit";
        }else {
            return "error";
        }
    }

    /*修改问题后submit按钮*/
    @PostMapping("/profile/issueUpdate")
    public String postQuestion(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "id") Integer id,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "tag") String tag
            ) {
        CommunityQuestion communityQuestion = new CommunityQuestion();
        communityQuestion.setTitle(title);
        communityQuestion.setDescription(description);
        communityQuestion.setTag(tag);
        communityQuestion.setId(id);
        communityQuestion.setGmt_modified(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));

        questionMapper.updateQuestion(communityQuestion);

        return "redirect:/homepage";
    }

//    ================================================================================================
    /*进入单个question页面*/
    @GetMapping("/publish/issue{id}")
    public String findQuestionByIssueId(HttpServletRequest request,
                                        /*使用{param} + @PathVariable的方法来接收地址栏的某个特定变量*/
                                        @PathVariable String id) {
        CommunityQuestion questionByIssueId = questionMapper.findQuestionByIssueId(id);
        request.getSession().setAttribute("questionByIssueId",questionByIssueId);
        return "publish";
    }
}
