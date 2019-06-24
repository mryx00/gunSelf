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
import com.stylefeng.gunSelf.modular.system.model.BlogPhoto;
import com.stylefeng.gunSelf.modular.blog.service.IBlogPhotoService;
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
 * 博客相片控制器
 *
 * @author fengshuonan
 * @Date 2019-05-07 15:42:11
 */
@Controller
@RequestMapping("/blogPhoto")
public class BlogPhotoController extends BaseController {

    private String PREFIX = "/blog/blogPhoto/";

    @Autowired
    private IBlogPhotoService blogPhotoService;

    /**
     * 跳转到博客相片首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "blogPhoto.html";
    }

    /**
     * 跳转到添加博客相片
     */
    @RequestMapping("/blogPhoto_add")
    public String blogPhotoAdd() {
        return PREFIX + "blogPhoto_add.html";
    }

    /**
     * 跳转到修改博客相片
     */
    @RequestMapping("/blogPhoto_update/{blogPhotoId}")
    public String blogPhotoUpdate(@PathVariable Integer blogPhotoId, Model model) {
        BlogPhoto blogPhoto = blogPhotoService.selectById(blogPhotoId);
        model.addAttribute("item",blogPhoto);
        LogObjectHolder.me().set(blogPhoto);
        return PREFIX + "blogPhoto_edit.html";
    }

    /**
     * 获取博客相片列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<BlogPhoto> page = new PageFactory<BlogPhoto>().defaultPage();
        if (ToolUtil.isNotEmpty(condition)){
            blogPhotoService.selectPage(page,new EntityWrapper<BlogPhoto>().eq("account",ShiroKit.getUser().getAccount()).and().like("photoName",condition));
        }else{
            blogPhotoService.selectPage(page,new EntityWrapper<BlogPhoto>().eq("account",ShiroKit.getUser().getAccount()));
        }
        return super.packForBT(page);
    }

    /**
     * 新增博客相片
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BlogPhoto blogPhoto) {
        blogPhoto.setAccount(ShiroKit.getUser().getAccount());
        blogPhotoService.insert(blogPhoto);
        return SUCCESS_TIP;
    }

    /**
     * 删除博客相片
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("ids[]") List<Integer> ids) {
        blogPhotoService.deleteBatchIds(ids);;
        return SUCCESS_TIP;
    }

    /**
     * 修改博客相片
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BlogPhoto blogPhoto) {
        blogPhotoService.updateById(blogPhoto);
        return SUCCESS_TIP;
    }

    /**
     * 博客相片详情
     */
    @RequestMapping(value = "/detail/{blogPhotoId}")
    @ResponseBody
    public Object detail(@PathVariable("blogPhotoId") Integer blogPhotoId) {
        return blogPhotoService.selectById(blogPhotoId);
    }


    /**
     * 博客相片导出
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export() throws IOException {
           Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("博客相片",""),
           BlogPhoto.class,blogPhotoService.selectList(null));
           HttpServletResponse response = HttpKit.getResponse();
           response.setContentType("application/vnd.ms-excel;charset=utf-8");
           String file_name = URLEncoder.encode("博客相片.xls","UTF-8");
           response.setHeader("Content-Disposition","attachment;filename="+file_name);
           ServletOutputStream out=response.getOutputStream();
           workbook.write(out);
           workbook.close();
           out.close();
    }

    /**
     * 博客相片导入
     */
    @RequestMapping(value = "/import")
    @ResponseBody
    public Object importExcel(@RequestPart("file") MultipartFile excel) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<BlogPhoto> list = ExcelImportUtil.importExcel(excel.getInputStream(), BlogPhoto.class, params);
        blogPhotoService.insertBatch(list);
        return SUCCESS_TIP;
    }
}
