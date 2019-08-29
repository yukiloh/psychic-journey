package xyz.murasakichigo.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*使用thymeleaf解析一个欢迎页面*/
/*记得路径必须与启动类相同！！*/  /*不可用@RestController，会自动添加@ResponseBody导致页面解析错误*/
@Controller
public class MainController {

    @GetMapping("/greeting")        /*@GetMapping = @RequestMapping(method = RequestMethod.GET)*/
    /*@RequestParam中,required:可以不进行传参,并且defaultValue指定为World*/
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";  /*返回一个greeting.html页面,会默认从resources/templates/下查找; 方法上不可添加@ResponseBody!!*/
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
