package xyz.murasakichigo.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.murasakichigo.community.dto.TestDTO;
import xyz.murasakichigo.community.utils.FtpUtil;

import javax.servlet.http.HttpServletRequest;
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
    private FtpUtil ftpUtil;

    @PostMapping("/fileUpload")
    public @ResponseBody String TestFileUpload(HttpServletRequest request, MultipartFile upload) throws IOException {
        String realPath = request.getServletContext().getRealPath("/");

        String originalFilename = upload.getOriginalFilename();        /*使用upload（MultipartFile）获取文件名*/
        String name = UUID.randomUUID() + "_" + originalFilename;       /*使用upload中的transferTo存储文件*/
        File file = new File(realPath, name);
        upload.transferTo(file);
        System.out.println(file.getPath()); /*打印路径*/


        /*上传至ftp服务器*/

        if (ftpUtil.uploadToFtp(file)){
            System.out.println("上传至ftp服务器！");
        }else {
            System.out.println("上传至ftp服务器失败!");
        }
        boolean isDeleted = file.delete();
        if (isDeleted) {
            System.out.println("本地文件删除成功");
        }else System.out.println("本地文件删除失败");
        return "success";
    }





}
