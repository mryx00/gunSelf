package com.stylefeng.gunSelf.modular.blog.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.shiro.ShiroKit;
import com.stylefeng.gunSelf.core.shiro.ShiroUser;
import com.stylefeng.gunSelf.core.util.ToolUtil;
import com.stylefeng.gunSelf.modular.blog.service.IBlogTopService;
import com.stylefeng.gunSelf.modular.blog.service.IBlogViewService;
import com.stylefeng.gunSelf.modular.blog.vo.BlogCommentInfo;
import com.stylefeng.gunSelf.modular.blog.service.IBlogContentService;
import com.stylefeng.gunSelf.modular.system.model.BlogContent;
import com.stylefeng.gunSelf.modular.system.model.BlogTop;
import com.stylefeng.gunSelf.modular.system.model.BlogView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.BlogComment;
import com.stylefeng.gunSelf.modular.blog.service.IBlogCommentService;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;

import java.util.List;

/**
 * 博客内容控制器
 *
 * @author fengshuonan
 * @Date 2018-09-02 22:02:01
 */
@Controller
@RequestMapping("/blogComment")
public class BlogCommentController extends BaseController {

    private String PREFIX = "/blog/blogComment/";

    @Autowired
    private IBlogCommentService blogCommentService;

    @Autowired
    private IBlogContentService blogContentService;

    @Autowired
    private IBlogViewService blogViewService;

    @Autowired
    private IBlogTopService blogTopService;


    /**
     * 跳转到博客内容首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "blogComment.html";
    }

    /**
     * 跳转到添加博客内容
     */
    @RequestMapping("/blogComment_add")
    public String blogCommentAdd() {
        return PREFIX + "blogComment_add.html";
    }

    /**
     * 跳转到修改博客内容
     */
    @RequestMapping("/blogComment_update/{blogCommentId}")
    public String blogCommentUpdate(@PathVariable Integer blogCommentId, Model model) {
        BlogComment blogComment = blogCommentService.selectById(blogCommentId);
        model.addAttribute("item",blogComment);
        LogObjectHolder.me().set(blogComment);
        return PREFIX + "blogComment_edit.html";
    }

    /**
     * 获取博客内容列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<BlogCommentInfo> page = new PageFactory<BlogCommentInfo>().defaultPage();
        blogCommentService.selectCommentList(page,ShiroKit.getUser().getAccount(),condition);
        return super.packForBT(page);
    }

    /**
     * 新增博客评论内容
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BlogComment blogComment) {
        blogCommentService.insert(blogComment);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/add/{blogId}")
    public Object addComment(@PathVariable("blogId") String blogId,Model model) {
        BlogContent blogContent = blogContentService.selectById(blogId);
        ShiroUser user = ShiroKit.getUser();
        if (ToolUtil.isEmpty(user)) {
            getSession().setAttribute("redirectUrl","/blogComment/add/"+blogId);
            return REDIRECT + "/blog";
        }
        int viewCount = blogViewService.selectCount(new EntityWrapper<BlogView>().eq("articleId", blogId));
        int count = blogTopService.selectCount(new EntityWrapper<BlogTop>().eq("articleId",blogId));
        model.addAttribute("user",user);
        model.addAttribute("blogUser",user);
        model.addAttribute("blog",blogContent);
        model.addAttribute("viewCount",viewCount);
        model.addAttribute("topCount",count);
        return "/blog/html/comment.html";
    }

    /**
     * 删除博客评论内容
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer blogCommentId) {
        blogCommentService.deleteById(blogCommentId);
        return SUCCESS_TIP;
    }

    /**
     * 修改博客评论内容
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BlogComment blogComment) {
        blogCommentService.updateById(blogComment);
        return SUCCESS_TIP;
    }

    /**
     * 博客内容详情
     */
    @RequestMapping(value = "/detail/{blogCommentId}")
    @ResponseBody
    public Object detail(@PathVariable("blogCommentId") Integer blogCommentId) {
        return blogCommentService.selectById(blogCommentId);
    }

    @RequestMapping(value = "/showComments")
    public Object blogDetail(int blogId, String current, String size,String orderby,Model model ){
        List<BlogCommentInfo> blogCommentInfos = blogCommentService .selectByArticleId(blogId,Integer.valueOf(current),Integer.valueOf(size),orderby);
        model.addAttribute("blogCommentInfos",blogCommentInfos);
        return "/blog/html/details.html#usertable";
    }

}
