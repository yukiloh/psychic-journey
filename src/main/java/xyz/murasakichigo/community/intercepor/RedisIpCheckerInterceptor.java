package xyz.murasakichigo.community.intercepor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.murasakichigo.community.mapper.IIpMapper;
import xyz.murasakichigo.community.utils.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*拦截检查ip*/
@Component
public class RedisIpCheckerInterceptor implements HandlerInterceptor {

    private final IIpMapper ipMapper;

    public RedisIpCheckerInterceptor(IIpMapper ipMapper, RedisUtil redisUtil) {
        this.ipMapper = ipMapper;
        this.redisUtil = redisUtil;
    }

    /*预处理拦截内容，返回布尔值*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Boolean flag = checkAccessCounts(request);
        if (flag){
            return true;
        }else {
            System.out.println("over than max load times");
            /*超过访问次数,重定向至别人家的网站看风景*/
            response.sendRedirect("https://cn.bing.com/");
            return false;
        }
    }

    /*后处理*/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
//        System.out.println("post handle");
    }

    /*返回后处理*/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
//        System.out.println("after completion");
    }

    private final RedisUtil redisUtil;

    /*检查访问次数,当超过次数后会跳转至baidu*/
        private Boolean checkAccessCounts(HttpServletRequest request) {
            Integer maxCount = 1000;    /*可能因为拦截器的原因,每次ip count + 2*/

            String ipAddr = request.getRemoteAddr();/*获取用户地址*/
            Integer counts = redisUtil.findIPByRedis(ipAddr);
            if (counts >= maxCount) {
                /*当超出最大访问次数后更新数据库*/
                ipMapper.updateIp(ipAddr,maxCount);
                return false;
            }else return true;

        /*原读取数据库部分*/
//            Integer count = ipMapper.findCountByIp(ipAddr);
//            if (count != null&& count>= maxCount) {
//                return false;
//            }else if (count == null){
//                /*插入*/
//                ipMapper.createIp(ipAddr);
//            }else {
//                /*累加*/
//                ipMapper.updateIp(ipAddr);
//            }
//            return true;
//        }else return false;

    }
}
