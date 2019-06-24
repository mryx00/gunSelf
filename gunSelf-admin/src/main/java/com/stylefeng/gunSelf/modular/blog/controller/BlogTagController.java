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
import com.stylefeng.gunSelf.modular.system.model.BlogTag;
import com.stylefeng.gunSelf.modular.blog.service.IBlogTagService;
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
import java.util.List;;
import org.springframework.web.multipart.MultipartFile;;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import org.springframework.web.bind.annotation.*;;

/**
 * 博客标签控制器
 *
 * @author fengshuonan
 * @Date 2019-05-26 22:50:56
 */
@Controller
@RequestMapping("/blogTag")
public class BlogTagController extends BaseController {

    private String PREFIX = "/blog/blogTag/";

    @Autowired
    private IBlogTagService blogTagService;

    /**
     * 跳转到博客标签首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "blogTag.html";
    }

    /**
     * 跳转到添加博客标签
     */
    @RequestMapping("/blogTag_add")
    public String blogTagAdd() {
        return PREFIX + "blogTag_add.html";
    }

    /**
     * 跳转到修改博客标签
     */
    @RequestMapping("/blogTag_update/{blogTagId}")
    public String blogTagUpdate(@PathVariable Integer blogTagId, Model model) {
        BlogTag blogTag = blogTagService.selectById(blogTagId);
        model.addAttribute("item",blogTag);
        LogObjectHolder.me().set(blogTag);
        return PREFIX + "blogTag_edit.html";
    }

    /**
     * 获取博客标签列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<BlogTag> page = new PageFactory<BlogTag>().defaultPage();
        if (ToolUtil.isNotEmpty(condition)){
            blogTagService.selectPage(page,new EntityWrapper<BlogTag>().eq("account",ShiroKit.getUser().getAccount()).and().like("tagName",condition));
        }else{
            blogTagService.selectPage(page,new EntityWrapper<BlogTag>().eq("account",ShiroKit.getUser().getAccount()));
        }

        return super.packForBT(page);
    }

    /**
     * 新增博客标签
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BlogTag blogTag) {
        blogTag.setAccount(ShiroKit.getUser().getAccount());
        blogTagService.insert(blogTag);
        return SUCCESS_TIP;
    }

    /**
     * 删除博客标签
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("ids[]") List<Integer> ids) {
        blogTagService.deleteBatchIds(ids);;
        return SUCCESS_TIP;
    }

    /**
     * 修改博客标签
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BlogTag blogTag) {
        blogTagService.updateById(blogTag);
        return SUCCESS_TIP;
    }

    /**
     * 博客标签详情
     */
    @RequestMapping(value = "/detail/{blogTagId}")
    @ResponseBody
    public Object detail(@PathVariable("blogTagId") Integer blogTagId) {
        return blogTagService.selectById(blogTagId);
    }


    /**
     * 博客标签导出
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export() throws IOException {
           Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("博客标签",""),
           BlogTag.class,blogTagService.selectList(null));
           HttpServletResponse response = HttpKit.getResponse();
           response.setContentType("application/vnd.ms-excel;charset=utf-8");
           String file_name = URLEncoder.encode("博客标签.xls","UTF-8");
           response.setHeader("Content-Disposition","attachment;filename="+file_name);
           ServletOutputStream out=response.getOutputStream();
           workbook.write(out);
           workbook.close();
           out.close();
    }

    /**
     * 博客标签导入
     */
    @RequestMapping(value = "/import")
    @ResponseBody
    public Object importExcel(@RequestPart("file") MultipartFile excel) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<BlogTag> list = ExcelImportUtil.importExcel(excel.getInputStream(), BlogTag.class, params);
        blogTagService.insertBatch(list);
        return SUCCESS_TIP;
    }
}
