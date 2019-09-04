package xyz.murasakichigo.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.murasakichigo.community.dto.CommunityQuestion;
import xyz.murasakichigo.community.dto.CommunityUser;
import xyz.murasakichigo.community.mapper.IQuestionMapper;
import xyz.murasakichigo.community.mapper.IUserMapper;

import java.text.SimpleDateFormat;

/*用于提交问题的控制器*/
@Controller
public class QuestionController {

    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private IQuestionMapper questionMapper;


    /*当接收到get时原路返回(理由不明)*/
    @GetMapping("/questionSubmit")
    public String getQuestion() {
        return "question";
    }

    /*使用post接受*/
    @PostMapping("/questionSubmit")
    public String postQuestion(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "tag") String tag,
            @CookieValue(value = "token")String token) {

        CommunityQuestion communityQuestion = new CommunityQuestion();
        communityQuestion.setTitle(title);
        communityQuestion.setDescription(description);
        communityQuestion.setTag(tag);
        communityQuestion.setAuthor_user_id(userMapper.findUserByToken(token).getId());
        communityQuestion.setGmt_create(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        communityQuestion.setTag(tag);

        System.out.println(communityQuestion);
        questionMapper.createIssue(communityQuestion);

        /*应该是重定向至成功页面,然后返回到问题浏览的...*/
        return "redirect:/login";
    }
}
