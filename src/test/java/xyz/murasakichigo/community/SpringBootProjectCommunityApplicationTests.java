package xyz.murasakichigo.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.murasakichigo.community.dto.AccessTokenDTO;
import xyz.murasakichigo.community.dto.CommunityQuestion;
import xyz.murasakichigo.community.dto.ReplyDTO;
import xyz.murasakichigo.community.mapper.IQuestionMapper;
import xyz.murasakichigo.community.mapper.IReplyMapper;
import xyz.murasakichigo.community.mapper.IUserMapper;

import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootProjectCommunityApplicationTests {



//    @Autowired
//    private IQuestionMapper questionMapper;

    @Test
    public void contextLoads() {
//        String keyword = "标";
//        List<CommunityQuestion> questionList = questionMapper.findKeyword(keyword);
//        for (CommunityQuestion c:questionList) {
//            System.out.println(c);

        int i = 1;
        if (++i == 1) {
            System.out.println(true);
        }else System.out.println(false);



    }



}
