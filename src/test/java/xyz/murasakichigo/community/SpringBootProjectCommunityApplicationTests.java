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



    @Autowired
    private IReplyMapper replyMapper;

    @Test
    public void contextLoads() {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setCritic_id(1);
        replyDTO.setReply_description("testtt");
        replyDTO.setParent_id(62);
        replyDTO.setGmt_reply_create(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        replyMapper.createReply(replyDTO);


    }



}
