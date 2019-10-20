package xyz.murasakichigo.community.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import xyz.murasakichigo.community.model.CommunityIssue;
import xyz.murasakichigo.community.mapper.IIpMapper;
import xyz.murasakichigo.community.mapper.IIssueMapper;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {


    /*redis记录问题，暂时禁用*/

    /*注入springboot自动配置的template，其泛型只能为Object或String*/
    private final RedisTemplate<Object,Object> redisTemplate;

    private final IIssueMapper questionMapper;

    public RedisUtil(RedisTemplate<Object, Object> redisTemplate, IIssueMapper questionMapper, IIpMapper ipMapper) {
        this.redisTemplate = redisTemplate;
        this.questionMapper = questionMapper;
        this.ipMapper = ipMapper;
    }

    public CommunityIssue findQuestionByIssueIdByRedis(String id){
        /*序列化key，使得key不为乱码（value无所谓）*/
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        /*从redis中获取key的值（通常返回的是object或string）*/
        CommunityIssue communityIssue = (CommunityIssue) redisTemplate.opsForValue().get("communityIssue"+id);

        if (communityIssue == null) {
            synchronized (this) {
                /*单例双锁;可能会出现object对象失效，所以再次获取对象；多线程中对象是不可靠的*/
                communityIssue = (CommunityIssue) redisTemplate.opsForValue().get("communityIssue"+id);
                if (communityIssue == null) {
                    communityIssue = questionMapper.findIssueByIssueId(id);
//                    System.out.println("find by sql");
                    redisTemplate.opsForValue().set("communityIssue"+id, communityIssue);        /*存储redis的key*/
                }
            }
        }/*else System.out.println("find by redis");*/
        return communityIssue;

    }


    /*redis记录ip*/
    private final IIpMapper ipMapper;

    public Integer findIPByRedis(String ip){
        /*如果ip存在于白名单内则不进行验证*/
        if (notInWhiteList(ip)) {
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            Integer ipCounts = (Integer)redisTemplate.opsForValue().get(ip);


            if (ipCounts != null) {
                ipCounts++;
                redisTemplate.opsForValue().set(ip,ipCounts,30,TimeUnit.MINUTES);   /*设置存活时间30分钟*/
                return ipCounts;
            }else {
                /*如首次访问则（检查并）存入数据库作为备份*/
                Integer count = ipMapper.findCountByIp(ip);
                if (count == null || count == 0) {
                    ipMapper.createIp(ip);
                }
                ipCounts = 1;
                redisTemplate.opsForValue().set(ip,ipCounts,30,TimeUnit.MINUTES);   /*2处都要设置存活时间，否则会后者会被覆盖*/

                return ipCounts;
            }
        }else return 0;

    }

    /*白名单*/
    private String[] whiteList = {"0:0:0:0:0:0:0:1","127.0.0.1"};
    private boolean notInWhiteList(String ip) {
        for (String checking : whiteList) {
            if (checking.equals(ip)) {
                return false;
            }
        }
        return true;
    }



}
