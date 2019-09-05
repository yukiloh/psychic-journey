package xyz.murasakichigo.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.murasakichigo.community.dto.AccessTokenDTO;
import xyz.murasakichigo.community.dto.CommunityQuestion;
import xyz.murasakichigo.community.dto.CommunityUser;
import xyz.murasakichigo.community.mapper.IQuestionMapper;
import xyz.murasakichigo.community.mapper.IUserMapper;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootProjectCommunityApplicationTests {


    @Autowired
    private AccessTokenDTO accessTokenDTO;
    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private IQuestionMapper questionMapper;

    /*测试读取yml中的数据*/
    @Value("${test.test1.test2}")
    private String a;

    /*测试读取yml中的github数据*/
    @Test
    public void contextLoads() {
//        System.out.println(accessTokenDTO.getClient_id());
//        System.out.println(accessTokenDTO.getClient_secret());
//        System.out.println(accessTokenDTO.getRedirect_uri());
//        System.out.println(a);

//        CommunityUser communityUser = userMapper.findAll().get(0);
//        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());/*获取当前时间的timestamp*/
//        communityUser.setGmt_create(datetime);
//        communityUser.setGmt_last_login(datetime);
        System.out.println(userMapper.findUserByToken("d9ad88cf-9577-4e09-8d09-768e095d5658"));


//        userMapper.updateUser(communityUser);

    }


    @Test
    public void contextLoads1() {
        Integer page = 3;
        page = (page-1)*10;
        List<CommunityQuestion> questionList = questionMapper.findQuestionByPage(page);
        for (CommunityQuestion q:questionList) {
            System.out.println(q);
        }
    }
}
