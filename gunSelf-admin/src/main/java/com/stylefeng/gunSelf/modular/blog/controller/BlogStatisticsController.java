package com.stylefeng.gunSelf.modular.blog.controller;

import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.modular.blog.service.IBlogCommentService;
import com.stylefeng.gunSelf.modular.blog.service.IBlogContentService;
import com.stylefeng.gunSelf.modular.blog.service.IBlogViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 博客统计控制器
 *
 * @author fengshuonan
 * @Date 2018-09-02 22:02:01
 */
@Controller
@RequestMapping("/blogStatistics")
public class BlogStatisticsController extends BaseController {

    private String PREFIX = "/blog/blogStatistics/";

    @Autowired
    private IBlogCommentService blogCommentService;

    @Autowired
    private IBlogContentService blogContentService;

    @Autowired
    private IBlogViewService blogViewService;


    /**
     * 跳转到博客统计首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "statistics.html";
    }



}
