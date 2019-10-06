package xyz.murasakichigo.community.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import xyz.murasakichigo.community.dto.CommunityQuestion;
import xyz.murasakichigo.community.mapper.IQuestionMapper;

@Component
public class RedisUtil {


    /*注入springboot自动配置的template，其泛型只能为Object或String*/
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private IQuestionMapper questionMapper;



    public CommunityQuestion findQuestionByIssueIdByRedis(String id){
        /*序列化key，使得key不为乱码（value无所谓）*/
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        /*从redis中获取key的值（通常返回的是object或string）*/
        CommunityQuestion communityQuestion = (CommunityQuestion) redisTemplate.opsForValue().get("communityQuestion"+id);

        if (communityQuestion == null) {
            synchronized (this) {
                /*单例双锁;可能会出现object对象失效，所以再次获取对象；多线程中对象是不可靠的*/
                communityQuestion = (CommunityQuestion) redisTemplate.opsForValue().get("communityQuestion"+id);
                if (communityQuestion == null) {
                    communityQuestion = questionMapper.findQuestionByIssueId(id);
//                    System.out.println("find by sql");
                    redisTemplate.opsForValue().set("communityQuestion"+id, communityQuestion);        /*存储redis的key*/
                }
            }
        }/*else System.out.println("find by redis");*/
        return communityQuestion;

    }
}