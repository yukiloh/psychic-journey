package xyz.murasakichigo.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.murasakichigo.community.dto.TestDTO;

import java.io.IOException;


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





}
