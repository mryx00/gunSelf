package com.stylefeng.gunSelf.modular.blog.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.shiro.ShiroKit;
import com.stylefeng.gunSelf.core.util.ToolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.BlogTopic;
import com.stylefeng.gunSelf.modular.blog.service.IBlogTopicService;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.stylefeng.gunSelf.core.support.HttpKit;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;;
import org.springframework.web.multipart.MultipartFile;;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import org.springframework.web.bind.annotation.*;;

/**
 * 博客专题控制器
 *
 * @author fengshuonan
 * @Date 2019-05-08 14:52:53
 */
@Controller
@RequestMapping("/blogTopic")
public class BlogTopicController extends BaseController {

    private String PREFIX = "/blog/blogTopic/";

    @Autowired
    private IBlogTopicService blogTopicService;

    /**
     * 跳转到博客专题首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "blogTopic.html";
    }

    /**
     * 跳转到添加博客专题
     */
    @RequestMapping("/blogTopic_add")
    public String blogTopicAdd() {
        return PREFIX + "blogTopic_add.html";
    }

    /**
     * 跳转到修改博客专题
     */
    @RequestMapping("/blogTopic_update/{blogTopicId}")
    public String blogTopicUpdate(@PathVariable Integer blogTopicId, Model model) {
        BlogTopic blogTopic = blogTopicService.selectById(blogTopicId);
        model.addAttribute("item",blogTopic);
        LogObjectHolder.me().set(blogTopic);
        return PREFIX + "blogTopic_edit.html";
    }

    /**
     * 获取博客专题列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<BlogTopic> page = new PageFactory<BlogTopic>().defaultPage();
        if (ToolUtil.isNotEmpty(condition)){
            blogTopicService.selectPage(page,new EntityWrapper<BlogTopic>().eq("account",ShiroKit.getUser().getAccount()).and().like("topicName",condition));
        }else{
            blogTopicService.selectPage(page,new EntityWrapper<BlogTopic>().eq("account",ShiroKit.getUser().getAccount()));
        }
        return super.packForBT(page);
    }

    /**
     * 新增博客专题
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BlogTopic blogTopic) {
        blogTopic.setCreateTime(new Date());
        blogTopic.setAccount(ShiroKit.getUser().getAccount());
        blogTopicService.insert(blogTopic);
        return SUCCESS_TIP;
    }

    /**
     * 删除博客专题
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("ids[]") List<Integer> ids) {
        blogTopicService.deleteBatchIds(ids);;
        return SUCCESS_TIP;
    }

    /**
     * 修改博客专题
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BlogTopic blogTopic) {
        blogTopicService.updateById(blogTopic);
        return SUCCESS_TIP;
    }

    /**
     * 博客专题详情
     */
    @RequestMapping(value = "/detail/{blogTopicId}")
    @ResponseBody
    public Object detail(@PathVariable("blogTopicId") Integer blogTopicId) {
        return blogTopicService.selectById(blogTopicId);
    }


    /**
     * 博客专题导出
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export() throws IOException {
           Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("博客专题",""),
           BlogTopic.class,blogTopicService.selectList(null));
           HttpServletResponse response = HttpKit.getResponse();
           response.setContentType("application/vnd.ms-excel;charset=utf-8");
           String file_name = URLEncoder.encode("博客专题.xls","UTF-8");
           response.setHeader("Content-Disposition","attachment;filename="+file_name);
           ServletOutputStream out=response.getOutputStream();
           workbook.write(out);
           workbook.close();
           out.close();
    }

    /**
     * 博客专题导入
     */
    @RequestMapping(value = "/import")
    @ResponseBody
    public Object importExcel(@RequestPart("file") MultipartFile excel) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<BlogTopic> list = ExcelImportUtil.importExcel(excel.getInputStream(), BlogTopic.class, params);
        blogTopicService.insertBatch(list);
        return SUCCESS_TIP;
    }
}
