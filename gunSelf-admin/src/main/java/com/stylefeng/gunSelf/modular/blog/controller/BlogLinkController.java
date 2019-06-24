package com.stylefeng.gunSelf.modular.blog.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.shiro.ShiroKit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.BlogLink;
import com.stylefeng.gunSelf.modular.blog.service.IBlogLinkService;
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
 * 博客外链接控制器
 *
 * @author fengshuonan
 * @Date 2019-05-07 09:43:05
 */
@Controller
@RequestMapping("/blogLink")
public class BlogLinkController extends BaseController {

    private String PREFIX = "/blog/blogLink/";

    @Autowired
    private IBlogLinkService blogLinkService;

    /**
     * 跳转到博客外链接首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "blogLink.html";
    }

    /**
     * 跳转到添加博客外链接
     */
    @RequestMapping("/blogLink_add")
    public String blogLinkAdd() {
        return PREFIX + "blogLink_add.html";
    }

    /**
     * 跳转到修改博客外链接
     */
    @RequestMapping("/blogLink_update/{blogLinkId}")
    public String blogLinkUpdate(@PathVariable Integer blogLinkId, Model model) {
        BlogLink blogLink = blogLinkService.selectById(blogLinkId);
        model.addAttribute("item",blogLink);
        LogObjectHolder.me().set(blogLink);
        return PREFIX + "blogLink_edit.html";
    }

    /**
     * 获取博客外链接列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<BlogLink> page = new PageFactory<BlogLink>().defaultPage();
        if (condition!=null){
            blogLinkService.selectPage(page,new EntityWrapper<BlogLink>().eq("account",ShiroKit.getUser().getAccount()).and().like("linkName", condition));
        }else{
            blogLinkService.selectPage(page,new EntityWrapper<BlogLink>().eq("account",ShiroKit.getUser().getAccount()));
        }
        return super.packForBT(page);
    }

    /**
     * 新增博客外链接
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BlogLink blogLink) {
        blogLink.setCount(0);
        blogLink.setCreateTime(new Date());
        blogLink.setAccount(ShiroKit.getUser().getAccount());
        blogLinkService.insert(blogLink);
        return SUCCESS_TIP;
    }

    /**
     * 删除博客外链接
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("ids[]") List<Integer> ids) {
        blogLinkService.deleteBatchIds(ids);;
        return SUCCESS_TIP;
    }

    /**
     * 修改博客外链接
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BlogLink blogLink) {
        blogLinkService.updateById(blogLink);
        return SUCCESS_TIP;
    }

    /**
     * 博客外链接详情
     */
    @RequestMapping(value = "/detail/{blogLinkId}")
    @ResponseBody
    public Object detail(@PathVariable("blogLinkId") Integer blogLinkId) {
        return blogLinkService.selectById(blogLinkId);
    }

    @RequestMapping(value = "/click")
    @ResponseBody
    public Object click(String id) {
        BlogLink blogLink = blogLinkService.selectById(id);
        blogLink.setCount(blogLink.getCount()+1);
        blogLinkService.updateById(blogLink);
        return SUCCESS_TIP;
    }

    /**
     * 博客外链接导出
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export() throws IOException {
           Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("博客外链接",""),
           BlogLink.class,blogLinkService.selectList(null));
           HttpServletResponse response = HttpKit.getResponse();
           response.setContentType("application/vnd.ms-excel;charset=utf-8");
           String file_name = URLEncoder.encode("博客外链接.xls","UTF-8");
           response.setHeader("Content-Disposition","attachment;filename="+file_name);
           ServletOutputStream out=response.getOutputStream();
           workbook.write(out);
           workbook.close();
           out.close();
    }

    /**
     * 博客外链接导入
     */
    @RequestMapping(value = "/import")
    @ResponseBody
    public Object importExcel(@RequestPart("file") MultipartFile excel) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<BlogLink> list = ExcelImportUtil.importExcel(excel.getInputStream(), BlogLink.class, params);
        blogLinkService.insertBatch(list);
        return SUCCESS_TIP;
    }
}
