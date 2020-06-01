package xyz.murasakichigo.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class SpringBootProjectCommunityApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));     //设置时区
        SpringApplication.run(SpringBootProjectCommunityApplication.class, args);
    }

}
