package xyz.murasakichigo.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.murasakichigo.community.dto.TestDTO;
import xyz.murasakichigo.community.mapper.IQuestionImgMapper;
import xyz.murasakichigo.community.utils.FtpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Controller
public class TestController {

    @GetMapping("/testpage")
    public String TestPage() {
        return "test";
    }

    /*@ResponseBody ReplyDTO*/
    @PostMapping("/testAjax")
    public @ResponseBody String TestAjax(@RequestBody String data
                                         ) throws IOException {
        System.out.println("Ajax asynchronous done!");
        System.out.println(data);           /*成功获取body内容*/
            return "success";
    }


    @Autowired
    private IQuestionImgMapper questionImgMapper;

    @GetMapping("/showImg")
    public String TestFileUpload(HttpServletRequest request){


        return "test";
    }





}
