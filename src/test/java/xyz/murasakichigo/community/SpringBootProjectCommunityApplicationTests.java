package xyz.murasakichigo.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.murasakichigo.community.utils.FtpUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootProjectCommunityApplicationTests {


    @Autowired
    private FtpUtil ftpUtil;

    @Test
    public void contextLoads() {
        ftpUtil.testFtp();
    }








}
