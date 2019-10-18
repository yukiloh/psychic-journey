package xyz.murasakichigo.community.controller;


import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.murasakichigo.community.dto.CommunityQuestion;
import xyz.murasakichigo.community.dto.CommunityUser;
import xyz.murasakichigo.community.dto.ReplyDTO;
import xyz.murasakichigo.community.mapper.IQuestionImgMapper;
import xyz.murasakichigo.community.mapper.IQuestionMapper;
import xyz.murasakichigo.community.mapper.IReplyMapper;
import xyz.murasakichigo.community.mapper.IUserMapper;
import xyz.murasakichigo.community.utils.FtpUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/*用于提交问题的控制器*/
@Controller
public class QuestionController {

    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private IQuestionMapper questionMapper;

    @Autowired
    private IReplyMapper replyMapper;

    @Autowired
    private FtpUtil ftpUtil;

    /*进入提交问题页面*/
    @GetMapping("/profile/newIssue")
    public String newIssue() {
        return "newIssue";
    }

    /*使用post接收*/
    @PostMapping("/profile/questionSubmit")
    public String postQuestion(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
//            @RequestParam(name = "tag") String tag,
//            @CookieValue(value = "token")String token,
            MultipartFile upload)  {
        String tag = "test";
        CommunityQuestion communityQuestion = new CommunityQuestion();
        communityQuestion.setTitle(title);
        communityQuestion.setDescription(description);
        communityQuestion.setTag(tag);

        /*通过shiro获取user*/
        CommunityUser user = (CommunityUser) SecurityUtils.getSubject().getPrincipal();
        communityQuestion.setAuthor_user_id(user.getId());
        communityQuestion.setAuthor_name(user.getUsername());

        /*已弃用token，改用从shiro获取user*/
//        Integer id = userMapper.findUserByToken(token).getId(); /*获取id*/
//        communityQuestion.setAuthor_user_id(id);
//        communityQuestion.setAuthor_name(userMapper.findUserById(id).getUsername());    /*通过id查找userName*/

        communityQuestion.setGmt_create(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        communityQuestion.setTag(tag);
        questionMapper.createIssue(communityQuestion);
        /*获取最后上传的问题id*/
        Integer maxIssueId = questionMapper.findMaxIssueId();

        /*如果upload不为空则上传*/
        if (!upload.isEmpty()) {
            uploadToServer(upload,maxIssueId);          /*上传至本地服务器*/
//            uploadToFtp(upload,request,maxIssueId);   /*上传至ftp服务器*/
        }

        /*重定向至问题*/
        return "redirect:/publish/issue"+maxIssueId;
    }

    @Autowired
    IQuestionImgMapper questionImgMapper;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    private void uploadToServer(MultipartFile upload, Integer maxIssueId) {
        String uploadedFileName =UUID.randomUUID() + "_" +upload.getOriginalFilename();
        try {
            upload.transferTo(new File((uploadFolder+uploadedFileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*将文件名更新至数据库*/
        questionImgMapper.createQuestionImgAddr(maxIssueId,uploadedFileName);
    }
        /*已弃用,无法解决读取问题,更换为本地上传*/
//    /*上传至FTP*/
//    private void uploadToFtp(MultipartFile upload, HttpServletRequest request, Integer maxIssueId) {
//        String realPath = request.getServletContext().getRealPath("/");
//        String originalFilename = upload.getOriginalFilename();        /*使用upload（MultipartFile）获取文件名*/
//        String name = UUID.randomUUID() + "_" + originalFilename;       /*使用upload中的transferTo存储文件*/
//        File file = new File(realPath, name);
//
//        try {
//            upload.transferTo(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        /*上传至ftp服务器*/   /*并更新img数据库*/
//        if (ftpUtil.uploadToFtp(file,maxIssueId)){
////            System.out.println("上传至ftp服务器！");
//        }else {
////            System.out.println("上传至ftp服务器失败!");
//        }
//        if (file.delete()) {
////            System.out.println("本地文件删除成功");
//        }else {
////            System.out.println("本地文件删除失败");
//        }
//    }


    //===================================================================
    /*进入问题修改*/
    @GetMapping("/profile/issueEdit/{id}")
    public String issueEdit(@PathVariable String id,
                            HttpServletRequest request) {
        /*验证是否issue作者ID与登陆者相同*/
        CommunityUser communityUser = (CommunityUser) request.getSession().getAttribute("communityUser");
        if (communityUser.getId().equals(questionMapper.findQuestionByIssueId(id).getAuthor_user_id())) {
            CommunityQuestion questionByIssueId = questionMapper.findQuestionByIssueId(id);
            request.getSession().setAttribute("questionByIssueId",questionByIssueId);
            return "issueEdit";
        }else {
            return "error";
        }
    }

    /*修改问题后submit按钮*/
    @PostMapping("/profile/issueUpdate")
    public String postQuestion(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "id") Integer id,
            @RequestParam(name = "description") String description
//            @RequestParam(name = "tag") String tag
            ) {
        CommunityQuestion communityQuestion = new CommunityQuestion();
        communityQuestion.setTitle(title);
        communityQuestion.setDescription(description);
//        communityQuestion.setTag(tag);
        communityQuestion.setId(id);
        communityQuestion.setGmt_modified(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));

        questionMapper.updateQuestion(communityQuestion);

        return "redirect:/homepage";
    }

//    ================================================================================================
    /*进入单个question页面*/

//    @Autowired
//    private RedisUtil redisUtil;

    @GetMapping("/publish/issue{id}")
    public String findQuestionByIssueId(HttpServletRequest request,
                                        /*使用{param} + @PathVariable的方法来接收地址栏的某个特定变量*/
                                        @PathVariable String id) {
        CommunityQuestion question = questionMapper.findQuestionByIssueId(id);        /*直接调用数据库*/
//        CommunityQuestion question = redisUtil.findQuestionByIssueIdByRedis(id);        /*通过redis缓存；因为阅读数会写入数据库，暂时注释*/

        /*累加阅读数*/
        accumulateView(question,id);

        /*显示图片*/
        String imgAddr = questionImgMapper.findQuestionImgById(Integer.valueOf(id));
        if (!"null".equals(imgAddr)) {
            request.getSession().setAttribute("imgAddr",imgAddr);
        }

        /*读取回复*/
        List<ReplyDTO> replyDTOList = replyMapper.findReplyByIssueId(id);
        if (replyDTOList != null) {
            request.getSession().setAttribute("replyList", replyDTOList);
            request.getSession().setAttribute("question",question);
        }
        return "publish";
   }


//    ================================================================================================
    /*回复按钮*/
    @PostMapping("/profile/replySubmit")
    public String postReply(
            @RequestParam(name = "parent_id") String parent_id,
            @RequestParam(name = "reply_description") String description,
            @RequestParam(name = "critic_id") String critic_id
            )  {

        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setCritic_id(Integer.valueOf(critic_id));
        replyDTO.setParent_id(Integer.valueOf(parent_id));
        replyDTO.setReply_description(description);
        replyDTO.setGmt_reply_create(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        replyMapper.createReply(replyDTO);

        /*应该是重定向至成功页面,然后返回到问题浏览的...*/
        return "redirect:/publish/issue"+parent_id;
    }


    /*问题搜索*/
    @GetMapping("/search")
    public String search(HttpServletRequest request,@RequestParam(name = "keyword") String keyword) {
        List<CommunityQuestion> questionList = questionMapper.findKeyword(keyword);
        request.getSession().setAttribute("questionList",questionList);
        return "search";
    }


//    ================================================================================================
    private void accumulateView(CommunityQuestion question, String id) {
        /*添加判断：如果ip不累计访问；暂时省略;应该存在事务管理，同时有多个访问时候；*/
        Integer view_count = question.getView_count();
        if (view_count == null) {
            view_count = 0;
        }
        int view = view_count + 1;
        questionMapper.updateQuestionView(view,id);

    }
}
