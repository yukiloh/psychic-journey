package xyz.murasakichigo.community.controller;


import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import xyz.murasakichigo.community.mapper.IIssueImgMapper;
import xyz.murasakichigo.community.mapper.IIssueMapper;
import xyz.murasakichigo.community.mapper.IReplyMapper;
import xyz.murasakichigo.community.model.CommunityIssue;
import xyz.murasakichigo.community.model.CommunityReply;
import xyz.murasakichigo.community.model.CommunityUser;
import xyz.murasakichigo.community.utils.CountPageUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/*用于提交问题的控制器*/
@Controller
public class IssueController {

    private final IIssueMapper issueMapper;
    private final IReplyMapper replyMapper;

    public IssueController(IIssueMapper issueMapper, IReplyMapper replyMapper, IIssueImgMapper issueImgMapper) {
        this.issueMapper = issueMapper;
        this.replyMapper = replyMapper;
        this.issueImgMapper = issueImgMapper;
    }

//    ================================================================================================
    /*进入单个issue页面*/

//    @Autowired
//    private RedisUtil redisUtil;

    @GetMapping("/publish/issue/{id}")
    public String findIssueByIssueId(HttpServletRequest request,
            /*使用{param} + @PathVariable的方法来接收地址栏的某个特定变量*/
                                     @PathVariable String id) {
        CommunityIssue issue = issueMapper.findIssueByUserId(id);        /*直接调用数据库*/
//        CommunityIssue issue = redisUtil.findQIssueByIssueIdByRedis(id);        /*通过redis缓存；因为阅读数会写入数据库，暂时注释*/
        request.getSession().setAttribute("issue", issue);

        /*累加阅读数*/
        accumulateView(issue,id);

        /*显示图片*/
        String imgAddr = issueImgMapper.findIssueImgById(Integer.valueOf(id));
        if (!"null".equals(imgAddr)) {
            request.getSession().setAttribute("imgAddr",imgAddr);
        }

        /*读取回复*/
        List<CommunityReply> communityReplyList = replyMapper.findReplyByIssueId(id);
        if (communityReplyList != null) {
            request.getSession().setAttribute("replyList", communityReplyList);
        }
        return "issuePage";
    }

    /*提交问题*/
    @GetMapping("/profile/newIssue")
    public String newIssue() {
        return "issuePublish";
    }

    /*使用post接收*/
    @PostMapping("/profile/issueSubmit")
    public String postIssue(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
//            @RequestParam(name = "tag") String tag,
//            @CookieValue(value = "token")String token,
            MultipartFile upload)  {
        String tag = "test";
        CommunityIssue communityIssue = new CommunityIssue();
        communityIssue.setTitle(title);
        communityIssue.setDescription(description);
        communityIssue.setTag(tag);

        /*通过shiro获取user*/
        CommunityUser user = (CommunityUser) SecurityUtils.getSubject().getPrincipal();
        communityIssue.setAuthor_user_id(user.getId());
        communityIssue.setAuthor_name(user.getUsername());

        /*已弃用token，改用从shiro获取user*/
//        Integer id = userMapper.findUserByToken(token).getId(); /*获取id*/
//        communityIssue.setAuthor_user_id(id);
//        communityIssue.setAuthor_name(userMapper.findUserById(id).getUsername());    /*通过id查找userName*/

        communityIssue.setGmt_create(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        communityIssue.setTag(tag);
        issueMapper.createIssue(communityIssue);
        /*获取最后上传的问题id*/
        Integer maxIssueId = issueMapper.findMaxIssueId();

        /*如果upload不为空则上传*/
        if (!upload.isEmpty()) {
            uploadToServer(upload,maxIssueId);          /*上传至本地服务器*/
//            uploadToFtp(upload,request,maxIssueId);   /*上传至ftp服务器*/
        }

        /*重定向至问题*/
        return "redirect:/publish/issue/"+maxIssueId;
    }

    private final IIssueImgMapper issueImgMapper;

    @Value("${file.uploadFolder}")
    private String uploadFolder;
//    @Autowired
//    private FtpUtil ftpUtil;
    private void uploadToServer(MultipartFile upload, Integer maxIssueId) {
        String uploadedFileName =UUID.randomUUID() + "_" +upload.getOriginalFilename();
        try {
            upload.transferTo(new File((uploadFolder+uploadedFileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*将文件名更新至数据库*/
        issueImgMapper.createIssueImgAddr(maxIssueId,uploadedFileName);
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
    /*问题修改*/
    @GetMapping("/profile/issueEdit/{id}")
    public String issueEdit(@PathVariable String id,
                            HttpServletRequest request) {
        /*验证是否issue作者ID与登陆者相同*/
        CommunityUser communityUser = (CommunityUser) request.getSession().getAttribute("communityUser");
        if (communityUser.getId().equals(issueMapper.findIssueByUserId(id).getAuthor_user_id())) {
            CommunityIssue issue = issueMapper.findIssueByUserId(id);
            request.getSession().setAttribute("issue",issue);
            return "issueEdit";
        }else {
            return "error";
        }
    }

    /*修改后submit按钮*/
    @PostMapping("/profile/issueUpdate")
    public String postIssue(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "id") Integer id,
            @RequestParam(name = "description") String description
//            @RequestParam(name = "tag") String tag
            ) {
        CommunityIssue communityIssue = new CommunityIssue();
        communityIssue.setTitle(title);
        communityIssue.setDescription(description);
//        communityIssue.setTag(tag);
        communityIssue.setId(id);
        communityIssue.setGmt_modified(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));

        issueMapper.updateIssue(communityIssue);

        return "redirect:/homepage";
    }


    /*删除问题*/
    @GetMapping("/profile/issueEdit/delete/{id}")
    public String issueDelete(@PathVariable String id){
        CommunityUser user = (CommunityUser) SecurityUtils.getSubject().getPrincipal();
        if (user.getId().equals(issueMapper.findUserIdByIssueId(id))) {
            System.out.println("get user");
            issueMapper.deleteIssueByIssueId(id);
            System.out.println("del issue");
            replyMapper.deleteReplyByParentId(id);
            System.out.println("del reply");
            issueImgMapper.markDeletedImg(id);  /*标记被删除的问题*/
            System.out.println("mark img");
            return "redirect:/homepage";
        }else return "error";


//        return "redirect:/homepage";
    }

//    @PostMapping("/profile/issueEdit/delete.do")
//    public String IssueDeleteDo(@RequestParam(name = "id") String id){
//        CommunityUser user = (CommunityUser) SecurityUtils.getSubject().getPrincipal();
//        if (user.getId().equals(issueMapper.findUserIdByIssueId(id))) {
//            issueMapper.deleteIssueByIssueId(id);
//            replyMapper.deleteReplyByParentId(id);
//            issueImgMapper.markDeletedImg(id);  /*标记被删除的问题*/
//
//            return "redirect:/homepage";
//        }else return "error";
//
//    }

//    ================================================================================================
    /*回复按钮*/
    @PostMapping("/profile/replySubmit")
    public String postReply(
            @RequestParam(name = "parent_id") String parent_id,
            @RequestParam(name = "reply_description") String description,
            @RequestParam(name = "critic_id") String critic_id
            )  {

        CommunityReply communityReply = new CommunityReply();
        communityReply.setCritic_id(Integer.valueOf(critic_id));
        communityReply.setParent_id(Integer.valueOf(parent_id));
        communityReply.setReply_description(description);

        communityReply.setGmt_reply_create(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        replyMapper.createReply(communityReply);

        /*应该是重定向至成功页面,然后返回到问题浏览的...*/
        return "redirect:/publish/issue/"+parent_id;
    }

    @GetMapping("/profile/delReply/{parentId}/{replyId}")
    public String delReply(@PathVariable String replyId,@PathVariable String parentId){
        CommunityUser user = (CommunityUser) SecurityUtils.getSubject().getPrincipal();
        if (user.getId().equals(replyMapper.findCriticIdByReplyId(replyId))) {   /*验证*/
            replyMapper.deleteReply(replyId);
            return "redirect:/publish/issue/"+parentId;
        }else return "error";

    }

//    ================================================================================================
    /*问题搜索*/
    @GetMapping("/search")
    public String search(HttpServletRequest request,@RequestParam(name = "keyword") String keyword) {
        /*展示搜索页面内容*/
        String page = request.getParameter("page");
        if (page == null || page.length() == 0) {
            page = "1";
        }
        showSearchPage(request,page,keyword);
        return "search";
    }


//    ================================================================================================
    private void accumulateView(CommunityIssue issue, String id) {
        /*后期添加判断：同个ip至累计5次访问；*/
        Integer view_count = issue.getView_count();
        if (view_count == null) {
            view_count = 0;
        }
        int view = view_count + 1;
        issueMapper.updateIssueView(view,id);

    }

    private void showSearchPage(HttpServletRequest request, String page, String keyword){
        Integer issueCount = issueMapper.countIssueByKeyword(keyword);
        int[] result = new CountPageUtil().countPaging(issueCount, page);
        List<CommunityIssue> issueList = issueMapper.findIssueByKeyword(keyword,result[2]);
        request.getSession().setAttribute("issueList",issueList);
        request.getSession().setAttribute("page",result[1]);
        request.getSession().setAttribute("maxPage",result[0]);
        request.getSession().setAttribute("keyword",keyword);
    }
}
