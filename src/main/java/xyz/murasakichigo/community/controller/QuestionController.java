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
        return "question";
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


    /*问题跳转*/
    @GetMapping("/publish/issue{id}")
    public String findQuestionByIssueId(HttpServletRequest request,
                                        /*使用{param} + @PathVariable的方法来接受地址栏的某个特定变量*/
                                        @PathVariable String id) {
        CommunityQuestion questionByIssueId = questionMapper.findQuestionByIssueId(id);
        request.getSession().setAttribute("questionByIssueId",questionByIssueId);
        return "publish";
    }
}
