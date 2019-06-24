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
import com.stylefeng.gunSelf.modular.system.model.BlogBackimg;
import com.stylefeng.gunSelf.modular.blog.service.IBlogBackimgService;
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
 * 博客背景控制器
 *
 * @author fengshuonan
 * @Date 2019-05-13 12:31:42
 */
@Controller
@RequestMapping("/blogBackimg")
public class BlogBackimgController extends BaseController {

    private String PREFIX = "/blog/blogBackimg/";

    @Autowired
    private IBlogBackimgService blogBackimgService;

    /**
     * 跳转到博客背景首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "blogBackimg.html";
    }

    /**
     * 跳转到添加博客背景
     */
    @RequestMapping("/blogBackimg_add")
    public String blogBackimgAdd() {
        return PREFIX + "blogBackimg_add.html";
    }

    /**
     * 跳转到修改博客背景
     */
    @RequestMapping("/blogBackimg_update/{blogBackimgId}")
    public String blogBackimgUpdate(@PathVariable Integer blogBackimgId, Model model) {
        BlogBackimg blogBackimg = blogBackimgService.selectById(blogBackimgId);
        model.addAttribute("item",blogBackimg);
        LogObjectHolder.me().set(blogBackimg);
        return PREFIX + "blogBackimg_edit.html";
    }

    /**
     * 获取博客背景列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<BlogBackimg> page = new PageFactory<BlogBackimg>().defaultPage();
        if (ToolUtil.isNotEmpty(condition)){
            blogBackimgService.selectPage(page,new EntityWrapper<BlogBackimg>().eq("blogAccout",ShiroKit.getUser().getAccount()).and().like("imgName",condition));
        }else{
            blogBackimgService.selectPage(page,new EntityWrapper<BlogBackimg>().eq("blogAccout",ShiroKit.getUser().getAccount()));
        }

        return super.packForBT(page);
    }

    /**
     * 新增博客背景
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BlogBackimg blogBackimg) {
        blogBackimg.setBlogAccout(ShiroKit.getUser().getAccount());
        blogBackimgService.insert(blogBackimg);
        return SUCCESS_TIP;
    }

    /**
     * 删除博客背景
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("ids[]") List<Integer> ids) {
        blogBackimgService.deleteBatchIds(ids);;
        return SUCCESS_TIP;
    }

    /**
     * 修改博客背景
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BlogBackimg blogBackimg) {
        blogBackimgService.updateById(blogBackimg);
        return SUCCESS_TIP;
    }

    /**
     * 博客背景详情
     */
    @RequestMapping(value = "/detail/{blogBackimgId}")
    @ResponseBody
    public Object detail(@PathVariable("blogBackimgId") Integer blogBackimgId) {
        return blogBackimgService.selectById(blogBackimgId);
    }


    /**
     * 博客背景导出
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export() throws IOException {
           Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("博客背景",""),
           BlogBackimg.class,blogBackimgService.selectList(null));
           HttpServletResponse response = HttpKit.getResponse();
           response.setContentType("application/vnd.ms-excel;charset=utf-8");
           String file_name = URLEncoder.encode("博客背景.xls","UTF-8");
           response.setHeader("Content-Disposition","attachment;filename="+file_name);
           ServletOutputStream out=response.getOutputStream();
           workbook.write(out);
           workbook.close();
           out.close();
    }

    /**
     * 博客背景导入
     */
    @RequestMapping(value = "/import")
    @ResponseBody
    public Object importExcel(@RequestPart("file") MultipartFile excel) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<BlogBackimg> list = ExcelImportUtil.importExcel(excel.getInputStream(), BlogBackimg.class, params);
        blogBackimgService.insertBatch(list);
        return SUCCESS_TIP;
    }
}
