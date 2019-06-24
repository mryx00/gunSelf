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
import com.stylefeng.gunSelf.modular.system.model.BlogMusic;
import com.stylefeng.gunSelf.modular.blog.service.IBlogMusicService;
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
 * 博客音乐控制器
 *
 * @author fengshuonan
 * @Date 2019-05-07 05:01:44
 */
@Controller
@RequestMapping("/blogMusic")
public class BlogMusicController extends BaseController {

    private String PREFIX = "/blog/blogMusic/";

    @Autowired
    private IBlogMusicService blogMusicService;

    /**
     * 跳转到博客音乐首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "blogMusic.html";
    }

    /**
     * 跳转到添加博客音乐
     */
    @RequestMapping("/blogMusic_add")
    public String blogMusicAdd() {
        return PREFIX + "blogMusic_add.html";
    }

    /**
     * 跳转到修改博客音乐
     */
    @RequestMapping("/blogMusic_update/{blogMusicId}")
    public String blogMusicUpdate(@PathVariable Integer blogMusicId, Model model) {
        BlogMusic blogMusic = blogMusicService.selectById(blogMusicId);
        model.addAttribute("item",blogMusic);
        LogObjectHolder.me().set(blogMusic);
        return PREFIX + "blogMusic_edit.html";
    }

    /**
     * 获取博客音乐列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<BlogMusic> page = new PageFactory<BlogMusic>().defaultPage();
        if(ToolUtil.isNotEmpty(condition)){
            blogMusicService.selectPage(page,new EntityWrapper<BlogMusic>().eq("account",ShiroKit.getUser().getAccount()).and().like("musicName",condition));
        }else{
            blogMusicService.selectPage(page,new EntityWrapper<BlogMusic>().eq("account",ShiroKit.getUser().getAccount()));
        }

        return super.packForBT(page);
    }

    /**
     * 新增博客音乐
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BlogMusic blogMusic) {
        blogMusic.setAccount(ShiroKit.getUser().getAccount());
        blogMusicService.insert(blogMusic);
        return SUCCESS_TIP;
    }


    /**
     * 删除博客音乐
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("ids[]") List<Integer> ids) {
        blogMusicService.deleteBatchIds(ids);;
        return SUCCESS_TIP;
    }

    /**
     * 修改博客音乐
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BlogMusic blogMusic) {
        blogMusicService.updateById(blogMusic);
        return SUCCESS_TIP;
    }

    /**
     * 博客音乐详情
     */
    @RequestMapping(value = "/detail/{blogMusicId}")
    @ResponseBody
    public Object detail(@PathVariable("blogMusicId") Integer blogMusicId) {
        return blogMusicService.selectById(blogMusicId);
    }


    /**
     * 博客音乐导出
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export() throws IOException {
           Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("博客音乐",""),
           BlogMusic.class,blogMusicService.selectList(null));
           HttpServletResponse response = HttpKit.getResponse();
           response.setContentType("application/vnd.ms-excel;charset=utf-8");
           String file_name = URLEncoder.encode("博客音乐.xls","UTF-8");
           response.setHeader("Content-Disposition","attachment;filename="+file_name);
           ServletOutputStream out=response.getOutputStream();
           workbook.write(out);
           workbook.close();
           out.close();
    }

    /**
     * 博客音乐导入
     */
    @RequestMapping(value = "/import")
    @ResponseBody
    public Object importExcel(@RequestPart("file") MultipartFile excel) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<BlogMusic> list = ExcelImportUtil.importExcel(excel.getInputStream(), BlogMusic.class, params);
        blogMusicService.insertBatch(list);
        return SUCCESS_TIP;
    }
}
