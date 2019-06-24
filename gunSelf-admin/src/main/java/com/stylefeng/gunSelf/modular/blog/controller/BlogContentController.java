package com.stylefeng.gunSelf.modular.blog.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.shiro.ShiroKit;
import com.stylefeng.gunSelf.core.shiro.ShiroUser;
import com.stylefeng.gunSelf.core.util.ToolUtil;
import com.stylefeng.gunSelf.modular.blog.service.*;
import com.stylefeng.gunSelf.modular.blog.vo.*;
import com.stylefeng.gunSelf.modular.system.model.*;
import com.stylefeng.gunSelf.modular.system.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客内容控制器
 *
 * @author fengshuonan
 * @Date 2018-09-04 10:56:00
 */
@Controller
@RequestMapping("/blogContent")
public class BlogContentController extends BaseController {

    private static ObjectMapper MAPPER = new ObjectMapper();

    private String PREFIX = "/blog/blogContent/";

    @Autowired
    private IBlogContentService blogContentService;

    @Autowired
    private IBlogCommentService blogCommentService;

    @Autowired
    private IUserService userService;
    @Autowired
    private IBlogViewService blogViewService;
    @Autowired
    private IBlogTopService blogTopService;

    @Autowired
    private IBlogLinkService blogLinkService;

    @Autowired
    private IBlogPhotoService blogPhotoService;

    @Autowired
    private IBlogTagService blogTagService;

    @Autowired
    private IBlogtagRelaService blogtagRelaService;

    @Autowired
    private IBlogTopicService blogTopicService;

    @Autowired
    private IBlogMusicService blogMusicService;

    @Autowired
    private IBlogBackimgService blogBackimgService;

    /**
     * 跳转到博客内容首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "blogContent.html";
    }

    /**
     * 跳转到添加博客内容
     */
    @RequestMapping("/blogContent_add")
    public String blogContentAdd(Model model) {
        String account = ShiroKit.getUser().getAccount();
        List<BlogTopic> blogTopics = blogTopicService.selectList(new EntityWrapper<BlogTopic>().eq("account", account));
        model.addAttribute("auther",ShiroKit.getUser().getAccount());
        model.addAttribute("blogTopics",blogTopics);
        return PREFIX + "blogContent_add.html";
    }

    /**
     * 跳转到修改博客内容
     */
    @RequestMapping("/blogContent_update/{blogContentId}")
    public String blogContentUpdate(@PathVariable Integer blogContentId, Model model) {
        BlogContent blogContent = blogContentService.selectById(blogContentId);
        List<BlogTopic> blogTopics = blogTopicService.selectList(new EntityWrapper<BlogTopic>().eq("account", ShiroKit.getUser().getAccount()));
        //查询tags
        List<BlogtagRela> BlogtagRelas = blogtagRelaService.selectList(new EntityWrapper<BlogtagRela>().eq("blogId", blogContentId));
        List<String> blogTags = new ArrayList<>();
        for (BlogtagRela blogtagRela:BlogtagRelas){
        String blogTag = blogTagService.selectById(blogtagRela.getTagId()).getTagName();
            blogTags.add(blogTag);
        }
        model.addAttribute("item",blogContent);
        model.addAttribute("blogTopics",blogTopics);

        model.addAttribute("blogTags",JSONUtils.toJSONString(blogTags));
        LogObjectHolder.me().set(blogContent);
        return PREFIX + "blogContent_edit.html";
    }

    /**
     * 获取博客内容列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<BlogContentInfo> page = new PageFactory<BlogContentInfo>().defaultPage();
        blogContentService.selectByArticleId(page,ShiroKit.getUser().getAccount(),null,condition,null);
        return super.packForBT(page);
    }

    /**
     * 新增博客内容
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @Transactional
    public Object add(@RequestParam(value = "tags[]",required = false) List<String> tags,BlogContent blogContent) {
       // Map<String, String> requestParameters = HttpKit.getRequestParameters();

       // System.out.println(requestParameters);
//       System.out.println(blogCtTags);
        blogContentService.insert(blogContent);
        if (ToolUtil.isNotEmpty(tags)){
            for (String tag:tags) {
                int tagId = 0;
                List<BlogTag> blogTags = blogTagService.selectList(new EntityWrapper<BlogTag>().where("tagName", tag));
                if (blogTags!=null){
                    BlogTag blogTag = new BlogTag();
                    blogTag.setTagName(tag);
                    blogTag.setCount(1);
                    blogTag.setCreatTime(new Date());
                    blogTag.setAccount(ShiroKit.getUser().getAccount());
                    blogTagService.insert(blogTag);
                    tagId = blogTag.getId();
                }else{
                    BlogTag blogTag = blogTags.get(0);
                    blogTag.setCount(blogTag.getCount()+1);
                    blogTagService.updateById(blogTag);
                    tagId = blogTag.getId();
                }
                BlogtagRela blogtagRela = new BlogtagRela();
                blogtagRela.setTagId(tagId);
                blogtagRela.setBlogId(blogContent.getId());
                blogtagRelaService.insert(blogtagRela);
            }

        }

        return SUCCESS_TIP;
    }

    /**
     * 删除博客内容
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer blogContentId) {
        blogContentService.deleteById(blogContentId);
        //删除关系表
        blogtagRelaService.delete(new EntityWrapper<BlogtagRela>().eq("blogId",blogContentId));
        return SUCCESS_TIP;
    }

    /**
     * 修改博客内容
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @Transactional
    public Object update(@RequestParam(value = "tags[]",required = false) List<String> tags,BlogContent blogContent) {
        blogContentService.updateById(blogContent);
        List<BlogtagRela> BlogtagRelas = blogtagRelaService.selectList(new EntityWrapper<BlogtagRela>().eq("blogId", blogContent.getId()));
        for (BlogtagRela blogtagRela:BlogtagRelas) {
            blogtagRelaService.deleteById(blogtagRela.getId());
        }
        if (ToolUtil.isNotEmpty(tags)){
            for (String tag:tags) {
                int tagId = 0;
                List<BlogTag> blogTags = blogTagService.selectList(new EntityWrapper<BlogTag>().where("tagName", tag));
                if (blogTags != null) {
                    BlogTag blogTag = new BlogTag();
                    blogTag.setTagName(tag);
                    blogTag.setCount(1);
                    blogTag.setCreatTime(new Date());
                    blogTag.setAccount(ShiroKit.getUser().getAccount());
                    blogTagService.insert(blogTag);
                    tagId = blogTag.getId();
                } else {
                    BlogTag blogTag = blogTags.get(0);
                    blogTag.setCount(blogTag.getCount() + 1);
                    blogTagService.updateById(blogTag);
                    tagId = blogTag.getId();
                }
                BlogtagRela blogtagRela = new BlogtagRela();
                blogtagRela.setTagId(tagId);
                blogtagRela.setBlogId(blogContent.getId());
                blogtagRelaService.insert(blogtagRela);
            }
        }
        return SUCCESS_TIP;
    }

    /**
     * 博客内容详情
     */
    @RequestMapping(value = "/detail/{blogContentId}")
    @ResponseBody
    public Object detail(@PathVariable("blogContentId") Integer blogContentId) {
        return blogContentService.selectById(blogContentId);
    }

    /**
     * 博客列表
     */
    @RequestMapping(value = "/show/{account}")
    public Object blogList(@PathVariable("account") String account,Model model) {
        model.addAttribute("account",account);
        String title = getPara("title");
        String orderby = getPara("orderby");
        String topic = getPara("topic");
        BlogTopic currentTopic = null;
        if (ToolUtil.isNotEmpty(topic)){
             currentTopic = blogTopicService.selectById(Integer.valueOf(topic));
        }
        List<User> users = userService.selectList(new EntityWrapper<User>().eq("account", account));
        ShiroUser user = ShiroKit.getUser();

        if (ToolUtil.isEmpty(users)){

            return "/404.html";
        }else{
            //获取博客用户
            User blogUser = users.get(0);
            model.addAttribute("blogUser",blogUser);
            //用户登陆后重新跳转到首页
            if (ToolUtil.isNotEmpty(user))
            getSession().setAttribute("redirectUrl","/blogContent/show/"+account);
        }
        //获取博客内容
        Page<BlogContentInfo> blogContentPage = new Page<>(1, 3);
        Page<BlogContentInfo> contentPage = blogContentService.selectByArticleId(blogContentPage,account,topic,title,orderby);

        for (BlogContentInfo blogContentInfo:contentPage.getRecords()) {
            List<BlogTag> blogTags = new ArrayList<BlogTag>();
            int blogId = blogContentInfo.getId();
            List<BlogtagRela> BlogtagRelas = blogtagRelaService.selectList(new EntityWrapper<BlogtagRela>().eq("blogId", blogId));
            for (BlogtagRela blogtagRela:BlogtagRelas) {
                int tagId = blogtagRela.getTagId();
                BlogTag blogTag = blogTagService.selectById(tagId);
                if (ToolUtil.isNotEmpty(blogTag)){
                    blogTags.add(blogTag);
                }
            }
            blogContentInfo.setBlogTags(blogTags);
        }

        //获取文章总数
        int articleSum = blogContentService.selectCount(new EntityWrapper<BlogContent>().eq("auther",account));
        //获取访问量
        Page<BlogViewInfo> defaultPage = new Page<>(1,15);
        blogViewService.selectViewList(defaultPage,account,null);
        int viewSum = defaultPage.getTotal();
        //获取外置链接
        List<BlogLink> blogLinks = blogLinkService.selectList(new EntityWrapper<BlogLink>().eq("account", account).orderBy("`order`"));
        //获取专题
        List<BlogTopic> blogTopics = blogTopicService.selectList(new EntityWrapper<BlogTopic>().eq("account", account).orderBy("`order`"));
        //获取个人相册
        List<BlogPhoto> blogPhotos = blogPhotoService.selectList(new EntityWrapper<BlogPhoto>().eq("account", account).orderBy("`order`"));
        //获取音乐
        List<BlogMusic> blogMusics = blogMusicService.selectList(new EntityWrapper<BlogMusic>().eq("account", account).and().eq("isUse",1));
        //获取标签云的信息
//        List<BlogTag> blogTags = blogTagService.selectList(new EntityWrapper<BlogTag>().setSqlSelect("tagName","count").eq("account",account));
        List<BlogTag> blogTags = blogTagService.selectList2(account);
        model.addAttribute("articleSum",articleSum);
        model.addAttribute("viewSum",viewSum);
        model.addAttribute("blogs",contentPage);
        model.addAttribute("title",title);
        model.addAttribute("orderby",orderby);
        model.addAttribute("current",blogContentPage.getCurrent());
        model.addAttribute("blogLinks",blogLinks);
        model.addAttribute("blogPhotos",blogPhotos);
        model.addAttribute("currentTopic",currentTopic);

        List<BlogBackimg> BlogBackimgs = blogBackimgService.
                selectList(new EntityWrapper<BlogBackimg>().eq("belong", "0").
                        and().eq("isUse","1").
                        and().eq("blogAccout",account));
        //获取图片背景
        if (ToolUtil.isNotEmpty(BlogBackimgs)){
            model.addAttribute("blogBackImg",BlogBackimgs.get(0));
        }
        if (ToolUtil.isNotEmpty(blogMusics)){
            model.addAttribute("blogMusic",blogMusics.get(0));
        }else{
            model.addAttribute("blogMusic",null);
        }
        model.addAttribute("blogTopics",blogTopics);
        try {
            String blogTagsJson = MAPPER.writeValueAsString(blogTags);
            model.addAttribute("blogTagsJson",blogTagsJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }




        return "/blog/html/index.html";
    }

    /**
     * 博客详细信息
     * @param blogId
     * @param model
     * @return
     */
    @RequestMapping(value = "/showDetails/{blogId}")
    public Object blogDetail(@PathVariable("blogId") String blogId,Model model){
        BlogContent blogContent = blogContentService.selectById(blogId);
        BlogTopic blogTopic = null;
        if (ToolUtil.isNotEmpty(blogContent.getTopic())){
             blogTopic = blogTopicService.selectById(blogContent.getTopic());
        }

        //获取博客的具体标签
        List<BlogTag> blogTags = new ArrayList<BlogTag>();
        List<BlogtagRela> BlogtagRelas = blogtagRelaService.selectList(new EntityWrapper<BlogtagRela>().eq("blogId", blogId));
        for (BlogtagRela blogtagRela:BlogtagRelas) {
            int tagId = blogtagRela.getTagId();
            BlogTag blogTag = blogTagService.selectById(tagId);
            if (ToolUtil.isNotEmpty(blogTag)){
                blogTags.add(blogTag);
            }
        }

        String auther = blogContent.getAuther();
        //获取最新评论
        Page<BlogCommentInfo> commentPage = new Page<>(1, 5);
        Page<BlogCommentInfo> commentInfoPage = blogCommentService.selectCommentList(commentPage, auther, null);


        //获取最热文章
        Page<BlogContentInfo> page = new Page<>(1,5);
        Page<BlogContentInfo> hotBlogs = blogContentService.selectByArticleId(page,auther,null,null, "top2");


        List<User> users = userService.selectList(new EntityWrapper<User>().eq("account", auther));
        //获取博客用户
        User blogUser = users.get(0);
        model.addAttribute("blogUser",blogUser);
        //访问量+1
        BlogView blogView = new BlogView();
        blogView.setViewDate(new Date());
        int topCount = blogTopService.selectCount(new EntityWrapper<BlogTop>().eq("articleId", blogId));
        if (ToolUtil.isNotEmpty(ShiroKit.getUser())){
            blogView.setViewPersonId(ShiroKit.getUser().id);
        }
        blogView.setArticleId(Integer.valueOf(blogId));
        blogViewService.insert(blogView);
        int viewCount = blogViewService.selectCount(new EntityWrapper<BlogView>().eq("articleId", blogId));
        int count = blogCommentService.selectCount(new EntityWrapper<BlogComment>().eq("articleId",blogId));

        //获取上一篇，下一篇博客
        List<BlogContent> afterBlogs = blogContentService.selectList(new EntityWrapper<BlogContent>().gt("date", blogContent.getDate()).and().eq("auther",auther).orderBy("date",false));
        List<BlogContent> beforeBlogs = blogContentService.selectList(new EntityWrapper<BlogContent>().lt("date", blogContent.getDate()).and().eq("auther",auther).orderBy("date",false));
        if (ToolUtil.isNotEmpty(afterBlogs)){
            model.addAttribute("afterBlog",afterBlogs.get(0));
        }else{
            model.addAttribute("afterBlog",null);
        }
        if (ToolUtil.isNotEmpty(beforeBlogs)){
            model.addAttribute("beforeBlog",beforeBlogs.get(0));
        }else{
            model.addAttribute("beforeBlog",null);
        }
        model.addAttribute("count",count);
        model.addAttribute("pageSize",3);
        model.addAttribute("blog",blogContent);
        model.addAttribute("viewCount",viewCount);
        model.addAttribute("topCount",topCount);
        model.addAttribute("blogTags",blogTags);
        model.addAttribute("blogTopic",blogTopic);
        //最新评论
        model.addAttribute("newComments",commentInfoPage.getRecords());
        //最热文章
        model.addAttribute("hotBlogs",hotBlogs);
        List<BlogBackimg> BlogBackimgs = blogBackimgService.
                selectList(new EntityWrapper<BlogBackimg>().eq("belong", "3").
                and().eq("isUse","1").
                and().eq("blogAccout",auther));
        //获取图片背景
        if (ToolUtil.isNotEmpty(BlogBackimgs)){
            model.addAttribute("blogBackImg",BlogBackimgs.get(0));
        }
        return "/blog/html/details.html";
    }


    /**
     * 博客翻页
     * @param blogPage
     * @param model
     * @return
     */
    @RequestMapping(value = "/nextPage")
    @ResponseBody
    public Object nextPage(BlogPage blogPage,Model model){
        Page<BlogContentInfo> blogContentPage = new Page<>(blogPage.getCurrent(), 3);
        String title = getPara("title");
        String orderby = getPara("orderby");
        Page<BlogContentInfo> contentPage = blogContentService.selectByArticleId(blogContentPage,blogPage.getAccount(),null,title,orderby);
        for (BlogContentInfo blogContentInfo:contentPage.getRecords()) {
            List<BlogTag> blogTags = new ArrayList<BlogTag>();
            int blogId = blogContentInfo.getId();
            List<BlogtagRela> BlogtagRelas = blogtagRelaService.selectList(new EntityWrapper<BlogtagRela>().eq("blogId", blogId));
            for (BlogtagRela blogtagRela:BlogtagRelas) {
                int tagId = blogtagRela.getTagId();
                BlogTag blogTag = blogTagService.selectById(tagId);
                if (ToolUtil.isNotEmpty(blogTag)){
                    blogTags.add(blogTag);
                }
            }
            blogContentInfo.setBlogTags(blogTags);
        }
        return contentPage.getRecords();
    }


}
