package com.stylefeng.gunSelf.modular.blog.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import com.stylefeng.gunSelf.core.support.HttpKit;
import com.stylefeng.gunSelf.core.util.ToolUtil;
import com.stylefeng.gunSelf.modular.blog.service.IBlogBackimgService;
import com.stylefeng.gunSelf.modular.blog.service.IBlogContentService;
import com.stylefeng.gunSelf.modular.blog.vo.BlogContentInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogBackimg;
import com.stylefeng.gunSelf.modular.system.model.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

;
;
;

/**
 * 博客背景控制器
 *
 * @author fengshuonan
 * @Date 2019-05-09 19:42:27
 */
@Controller
@RequestMapping("/blogTimeline")
public class BlogTimelineController extends BaseController {

    private String PREFIX = "/blog/html/";

    @Autowired
    private IBlogBackimgService blogBackimgService;
    @Autowired
    private IService<User> userService;
    @Autowired
    private IBlogContentService blogContentService;

    /**
     * 跳转到博客背景首页
     */
    @RequestMapping("/{account}")
    public String index(@PathVariable("account") String account,Model model) {
        List<User> users = userService.selectList(new EntityWrapper<User>().eq("account", account));
        if (ToolUtil.isEmpty(users)){
            return "/404.html";
        }else{
            //获取博客用户
            User blogUser = users.get(0);
            Page<BlogContentInfo> blogContentPage = new Page<>(1, 99999);
            Page<BlogContentInfo> contentPage = blogContentService.selectByArticleId(blogContentPage,account,null,null,null);
            List<BlogBackimg> BlogBackimgs = blogBackimgService.
                    selectList(new EntityWrapper<BlogBackimg>().eq("belong", "2").
                            and().eq("isUse","1").
                            and().eq("blogAccout",account));
            //获取图片背景
            if (ToolUtil.isNotEmpty(BlogBackimgs)){
                model.addAttribute("blogBackImg",BlogBackimgs.get(0));
            }
            model.addAttribute("blogUser",blogUser);
            model.addAttribute("contentPage",contentPage);
        }
        return PREFIX + "timeline.html";


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
        blogBackimgService.selectPage(page);
        return super.packForBT(page);
    }

    /**
     * 新增博客背景
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BlogBackimg blogBackimg) {
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
