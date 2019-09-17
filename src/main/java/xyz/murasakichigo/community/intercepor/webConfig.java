package xyz.murasakichigo.community.intercepor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*web的配置类（拦截器只是在其中！）*/
@Configuration
//@EnableWebMvc     /*启用后可以进行，包括放行静态资源的配置更改；不启用则会调用默认配置*/
public class webConfig implements WebMvcConfigurer {

    /*将session拦截器交给spring管理*/
    @Autowired
    private SessionInterceptor sessionInterceptor;
    @Autowired
    private CommunityUserInterceptor communityUserInterceptor;

    /*拦截器*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*自定义一个会话拦截器SessionInterceptor*/
        registry.addInterceptor(sessionInterceptor).
                addPathPatterns("/**").                                             /*拦截所有*/
                excludePathPatterns("/logout","/error");          /*放行路径*/

        /*可以添加多个registry.addInterceptor()*/
        /*拦截profile下的路径，仅验证cUser*/
        registry.addInterceptor(communityUserInterceptor).addPathPatterns("/profile","/profile/**").
                excludePathPatterns("/logout","/error");


    }

    /*关于定义拦截器的步骤：
    * 1.创建配置类，并实现接口WebMvcConfigure，添加注解@Configuration
    * 2.重写拦截器方法addInterceptors
    * 3.调用方法registry.addInterceptor，添加参数实现了HandlerInterceptor的拦截器
    * 4.在HandlerInterceptor类中重写预处理、后处理、返回处理的方法*/



}
