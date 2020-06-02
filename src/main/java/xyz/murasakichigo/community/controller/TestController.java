package xyz.murasakichigo.community.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.murasakichigo.community.model.CommunityUser;


@Controller
public class TestController {

    @GetMapping("/testAjaxPage")
    public String TestAjaxPage() {
        return "test";
    }

    /*@ResponseBody CommunityReply*/
    @PostMapping("/testAjax")
    public @ResponseBody String TestAjax(@RequestBody String data
                                         ) {
        System.out.println("Ajax asynchronous done!");
        System.out.println(data);           /*成功获取body内容*/
            return "success";
    }

    @GetMapping("/test")
    public String TestFileUpload(){
        return "test";
    }


    @GetMapping("/testShiro")
    public @ResponseBody String TestShiro(){
        CommunityUser user = (CommunityUser) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        System.out.println(username);

        return username;
    }

    @GetMapping("/testGithubUserForShiro")
    public @ResponseBody String TestGithubUserForShiro(){
        checkingGitHubAccountByShiro("yukiloh");
        CommunityUser user = (CommunityUser) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        System.out.println(username);
        return username;
    }
    private void checkingGitHubAccountByShiro(String username) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,"githubUser");
        subject.login(token);
    }



    @PostMapping("/testCheckbox")
    public @ResponseBody String TestCheckbox(
            @RequestParam(name = "hiddenInput") String isDelete,
            @RequestParam(name = "title") String title
            ){
        System.out.println("test");
        if ("on".equals(isDelete)){
            System.out.println(isDelete);
        }
        System.out.println(title);
        return "test";
    }

    @PostMapping("/testUpload")
    public @ResponseBody String TestUpload(MultipartFile upload){
        String name = upload.getName();
        System.out.println(name);
        return "success:"+name;
    }


}
