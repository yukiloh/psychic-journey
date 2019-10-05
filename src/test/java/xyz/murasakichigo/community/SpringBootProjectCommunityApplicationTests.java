package xyz.murasakichigo.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.murasakichigo.community.dto.AccessTokenDTO;
import xyz.murasakichigo.community.dto.CommunityQuestion;
import xyz.murasakichigo.community.dto.CommunityUser;
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

    }

    @Autowired
    private IUserMapper userMapper;

    /*注入springboot自动配置的template，其泛型只能为Object或String*/
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void contextLoads2() {
        List<CommunityUser> userList;

        /*序列化key，使得key不为乱码（value无所谓）*/
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        /*从redis中获取key=userList的值（通常返回的是object或string）*/
        List<CommunityUser> userListByRedis = (List<CommunityUser>) redisTemplate.opsForValue().get("userList");

        if (userListByRedis == null) {
            synchronized (this) {
                /*可能会出现object对象失效，所以再次获取对象；多线程中对象是不可靠的*/
                userListByRedis = (List<CommunityUser>) redisTemplate.opsForValue().get("userList");
                if (userListByRedis == null) {
                    System.out.println("checking in sql!");
                    userList = userMapper.findAll();
                    redisTemplate.opsForValue().set("userList", userList);        /*存储redis的key=userList*/
                }
            }
        } else System.out.println("find in redis!");
        if (userListByRedis != null) {
            for (CommunityUser u : userListByRedis) {
                System.out.println(u);
            }
        }

    }

}
