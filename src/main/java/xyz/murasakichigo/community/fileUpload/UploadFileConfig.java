package xyz.murasakichigo.community.fileUpload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.MultipartConfigElement;

@Configuration
@PropertySource("classpath:application.yml")
public class UploadFileConfig {

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(uploadFolder);
        //文件最大 2mb
        factory.setMaxFileSize(1024*1024*2);
//        factory.setMaxFileSize("5MB");
        // 设置总上传数据总大小   1G
//        factory.setMaxRequestSize(1024*1024*1024);
        return factory.createMultipartConfig();
    }
}