package xyz.murasakichigo.community.intercepor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*web的配置类（拦截器只是在其中！）*/
@Configuration
public class webConfig implements WebMvcConfigurer {

    /*将session拦截器交给spring管理*/
    @Autowired
    private SessionInterceptor sessionInterceptor;

    /*拦截器*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*自定义一个会话拦截器SessionInterceptor*/
        registry.addInterceptor(sessionInterceptor).
                addPathPatterns("/**").                     /*拦截所有*/
                excludePathPatterns("/static/**");          /*放行静态资源(不使用@EnableWebMvc不会进行拦截)*/
        /*可以添加多个registry.addInterceptor()*/
    }
}
