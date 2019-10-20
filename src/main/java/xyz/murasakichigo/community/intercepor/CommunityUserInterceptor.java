package xyz.murasakichigo.community.intercepor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.murasakichigo.community.model.CommunityUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*判断用户是否登陆*/
@Component
public class CommunityUserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("checking cUser");
        CommunityUser communityUser = (CommunityUser) request.getSession().getAttribute("communityUser");
        if (communityUser != null) {
            return true;
        }else {
            /*如果cUser为空则重定向至login*/
            response.sendRedirect("/login");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){

    }


}
