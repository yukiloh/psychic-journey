package xyz.murasakichigo.community.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import xyz.murasakichigo.community.mapper.IQuestionImgMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@Component
@PropertySource("classpath:application.yml")    /*指定读取的文件路径和名称*/
public class FtpUtil {

    /*从指定路径获取ftp的上传ip、用户、密码*/
    @Value("${ftp.ip}")
    private String ip;
    @Value("${ftp.user}")
    private String user;
    @Value("${ftp.password}")
    private String password;

    @Autowired
    private IQuestionImgMapper questionImgMapper;

    public boolean uploadToFtp(File file,Integer maxIssueId){
        FTPClient ftpClient = new FTPClient();

        try {
            //连接ftp服务器 参数填服务器的ip
            ftpClient.connect(ip);

            //进行登录 参数分别为账号 密码
            ftpClient.login(user,password);

            //改变工作目录（按自己需要是否改变）
            //只能选择local_root下已存在的目录
            ftpClient.changeWorkingDirectory("/home/upload/");

            //设置文件类型为二进制文件
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            //开启被动模式（按自己如何配置的ftp服务器来决定是否开启）
            ftpClient.enterLocalPassiveMode();

            //上传文件 参数：上传后的文件名，输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            ftpClient.storeFile(file.getName(), fileInputStream);
            /*更新图片至数据库*/
            questionImgMapper.createQuestionImgAddr(maxIssueId,file.getName());

            /*关闭流*/
            fileInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {

                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
