package com.stylefeng.gunSelf.modular.blog.controller;

import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.shiro.ShiroKit;
import com.stylefeng.gunSelf.modular.blog.vo.BlogViewInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.BlogView;
import com.stylefeng.gunSelf.modular.blog.service.IBlogViewService;
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
 * 控制器
 *
 * @author fengshuonan
 * @Date 2019-02-22 13:58:58
 */
@Controller
@RequestMapping("/blogView")
public class BlogViewController extends BaseController {

    private String PREFIX = "/blog/blogView/";

    @Autowired
    private IBlogViewService blogViewService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "blogView.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/blogView_add")
    public String blogViewAdd() {
        return PREFIX + "blogView_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/blogView_update/{blogViewId}")
    public String blogViewUpdate(@PathVariable Integer blogViewId, Model model) {
        BlogView blogView = blogViewService.selectById(blogViewId);
        model.addAttribute("item",blogView);
        LogObjectHolder.me().set(blogView);
        return PREFIX + "blogView_edit.html";
    }

    /**
     * 获取列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<BlogViewInfo> page = new PageFactory<BlogViewInfo>().defaultPage();
        blogViewService.selectViewList(page, ShiroKit.getUser().getAccount(),condition);
        return super.packForBT(page);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BlogView blogView) {
        blogViewService.insert(blogView);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("ids[]") List<Integer> ids) {
        blogViewService.deleteBatchIds(ids);;
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BlogView blogView) {
        blogViewService.updateById(blogView);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{blogViewId}")
    @ResponseBody
    public Object detail(@PathVariable("blogViewId") Integer blogViewId) {
        return blogViewService.selectById(blogViewId);
    }


    /**
     * 导出
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export() throws IOException {
           Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("",""),
           BlogView.class,blogViewService.selectList(null));
           HttpServletResponse response = HttpKit.getResponse();
           response.setContentType("application/vnd.ms-excel;charset=utf-8");
           String file_name = URLEncoder.encode(".xls","UTF-8");
           response.setHeader("Content-Disposition","attachment;filename="+file_name);
           ServletOutputStream out=response.getOutputStream();
           workbook.write(out);
           workbook.close();
           out.close();
    }

    /**
     * 导入
     */
    @RequestMapping(value = "/import")
    @ResponseBody
    public Object importExcel(@RequestPart("file") MultipartFile excel) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<BlogView> list = ExcelImportUtil.importExcel(excel.getInputStream(), BlogView.class, params);
        blogViewService.insertBatch(list);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/statistics")
    @ResponseBody
    public Object statistics(@RequestPart("file") MultipartFile excel) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<BlogView> list = ExcelImportUtil.importExcel(excel.getInputStream(), BlogView.class, params);
        blogViewService.insertBatch(list);
        return SUCCESS_TIP;
    }
}
