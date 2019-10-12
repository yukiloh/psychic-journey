package xyz.murasakichigo.community.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import xyz.murasakichigo.community.dto.CommunityQuestion;
import xyz.murasakichigo.community.mapper.IIpMapper;
import xyz.murasakichigo.community.mapper.IQuestionMapper;

@Component
public class RedisUtil {


    /*redis记录问题，暂时禁用*/

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


    /*redis记录ip*/
    @Autowired
    private IIpMapper ipMapper;

    public Integer findIPByRedis(String ip){
        if (ip != "0:0:0:0:0:0:0:1" && ip != "127.0.0.1") {
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            Integer ipCounts = (Integer)redisTemplate.opsForValue().get(ip);


            if (ipCounts != null) {
                ipCounts++;
                redisTemplate.opsForValue().set(ip,ipCounts);
                return ipCounts;
            }else {
                /*如首次访问则（检查并）存入数据库作为备份*/
                Integer count = ipMapper.findCountByIp(ip);
                if (count == null) {
                    ipMapper.createIp(ip);
                }
                ipCounts = 1;
                redisTemplate.opsForValue().set(ip,ipCounts);
                return ipCounts;
            }
        }else return 0;

    }

}
