package com.stylefeng.gunSelf.modular.blog.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.base.tips.ErrorTip;
import com.stylefeng.gunSelf.core.shiro.ShiroKit;
import com.stylefeng.gunSelf.core.util.ToolUtil;
import com.stylefeng.gunSelf.modular.blog.vo.BlogTopInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.BlogTop;
import com.stylefeng.gunSelf.modular.blog.service.IBlogTopService;
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
 * 博客点赞控制器
 *
 * @author fengshuonan
 * @Date 2018-09-25 18:31:38
 */
@Controller
@RequestMapping("/blogTop")
public class BlogTopController extends BaseController {

    private String PREFIX = "/blog/blogTop/";

    @Autowired
    private IBlogTopService blogTopService;

    /**
     * 跳转到博客点赞首页
     */
    @RequestMapping("")
    public String index() {

        return PREFIX + "blogTop.html";
    }

    /**
     * 跳转到添加博客点赞
     */
    @RequestMapping("/blogTop_add")
    public String blogTopAdd() {
        return PREFIX + "blogTop_add.html";
    }

    /**
     * 跳转到修改博客点赞
     */
    @RequestMapping("/blogTop_update/{blogTopId}")
    public String blogTopUpdate(@PathVariable Integer blogTopId, Model model) {
        BlogTop blogTop = blogTopService.selectById(blogTopId);
        model.addAttribute("item",blogTop);
        LogObjectHolder.me().set(blogTop);
        return PREFIX + "blogTop_edit.html";
    }

    /**
     * 获取博客点赞列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<BlogTopInfo> page = new PageFactory<BlogTopInfo>().defaultPage();
        blogTopService.selectTopList(page,ShiroKit.getUser().getAccount(),condition);
        return super.packForBT(page);
    }

    /**
     * 新增博客点赞
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BlogTop blogTop) {
        if (ToolUtil.isEmpty(blogTop.getTpDate())){
            blogTop.setTpDate(new Date());
            if (ToolUtil.isNotEmpty(ShiroKit.getUser())){
                int tpPersonId = ShiroKit.getUser().id;
                int count = blogTopService.selectCount(new EntityWrapper<BlogTop>().
                        eq("articleId", blogTop.getArticleId()).
                        and().eq("tpPersonId", tpPersonId));
                if (count>0){
                    return new ErrorTip(400,"已经进行过点赞");
                }
                blogTop.setTpPersonId(tpPersonId);
            }else{
                return new ErrorTip(400,"请先登录");
            }
        }
        blogTopService.insert(blogTop);
        return SUCCESS_TIP;
    }

    /**
     * 删除博客点赞
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("ids[]") List<Integer> ids) {
        blogTopService.deleteBatchIds(ids);;
        return SUCCESS_TIP;
    }

    /**
     * 修改博客点赞
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BlogTop blogTop) {
        blogTopService.updateById(blogTop);
        return SUCCESS_TIP;
    }

    /**
     * 博客点赞详情
     */
    @RequestMapping(value = "/detail/{blogTopId}")
    @ResponseBody
    public Object detail(@PathVariable("blogTopId") Integer blogTopId) {
        return blogTopService.selectById(blogTopId);
    }


    /**
     * 博客点赞导出
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export() throws IOException {
           Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("博客点赞",""),
           BlogTop.class,blogTopService.selectList(null));
           HttpServletResponse response = HttpKit.getResponse();
           response.setContentType("application/vnd.ms-excel;charset=utf-8");
           String file_name = URLEncoder.encode("博客点赞.xls","UTF-8");
           response.setHeader("Content-Disposition","attachment;filename="+file_name);
           ServletOutputStream out=response.getOutputStream();
           workbook.write(out);
           workbook.close();
           out.close();
    }

    /**
     * 博客点赞导入
     */
    @RequestMapping(value = "/import")
    @ResponseBody
    public Object importExcel(@RequestPart("file") MultipartFile excel) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<BlogTop> list = ExcelImportUtil.importExcel(excel.getInputStream(), BlogTop.class, params);
        blogTopService.insertBatch(list);
        return SUCCESS_TIP;
    }
}
