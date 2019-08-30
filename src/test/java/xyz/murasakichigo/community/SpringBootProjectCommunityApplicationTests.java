package xyz.murasakichigo.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.murasakichigo.community.dto.AccessTokenDTO;
import xyz.murasakichigo.community.mapper.IUserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootProjectCommunityApplicationTests {


    @Autowired
    private AccessTokenDTO accessTokenDTO;
    @Autowired
    private IUserMapper userMapper;

    /*测试读取yml中的数据*/
    @Value("${test.test1.test2}")
    private String a;

    /*测试读取yml中的github数据*/
    @Test
    public void contextLoads() {
        System.out.println(accessTokenDTO.getClient_id());
        System.out.println(accessTokenDTO.getClient_secret());
        System.out.println(accessTokenDTO.getRedirect_uri());
        System.out.println(a);

        System.out.println(userMapper.findAll());


    }

}
