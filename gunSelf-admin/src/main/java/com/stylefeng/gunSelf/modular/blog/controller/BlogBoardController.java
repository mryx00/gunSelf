package com.stylefeng.gunSelf.modular.blog.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.shiro.ShiroKit;
import com.stylefeng.gunSelf.core.util.ToolUtil;
import com.stylefeng.gunSelf.modular.blog.service.IBlogBackimgService;
import com.stylefeng.gunSelf.modular.blog.vo.BlogBoardInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogBackimg;
import com.stylefeng.gunSelf.modular.system.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.BlogBoard;
import com.stylefeng.gunSelf.modular.blog.service.IBlogBoardService;
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
 * 博客留言控制器
 *
 * @author fengshuonan
 * @Date 2019-05-12 18:17:36
 */
@Controller
@RequestMapping("/blogBoard")
public class BlogBoardController extends BaseController {

    private String PREFIX = "/blog/blogBoard/";

    @Autowired
    private IBlogBoardService blogBoardService;
    @Autowired
    private IService<User> userService;
    @Autowired
    private IBlogBackimgService blogBackimgService;

    /**
     * 跳转到博客留言首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "blogBoard.html";
    }

    @RequestMapping("/{account}")
    public String index2(@PathVariable("account") String account,Model model) {

        List<User> users = userService.selectList(new EntityWrapper<User>().eq("account", account));
        if (ToolUtil.isEmpty(users)){
            return "/404.html";
        }else{
            //获取博客用户
            User blogUser = users.get(0);
            model.addAttribute("blogUser",blogUser);
            if (ShiroKit.getUser()!=null){
                model.addAttribute("account", ShiroKit.getUser().getAccount());
            }else{
                model.addAttribute("account", "notLogin");
            }
        }
        model.addAttribute("count",blogBoardService.selectCount(new EntityWrapper<BlogBoard>().eq("blogAccount",account)));
//        Page<BlogBoardInfo> blogBoardInfoPage = blogBoardService.selectBlogBoardList(new Page<BlogBoardInfo>(1, 5), account);
//        model.addAttribute("blogBoardInfos",blogBoardInfoPage.getRecords());
        List<BlogBackimg> BlogBackimgs = blogBackimgService.
                selectList(new EntityWrapper<BlogBackimg>().eq("belong", "1").
                        and().eq("isUse","1").
                        and().eq("blogAccout",account));
        //获取图片背景
        if (ToolUtil.isNotEmpty(BlogBackimgs)){
            model.addAttribute("blogBackImg",BlogBackimgs.get(0));
        }
        return "/blog/html/"+ "board.html";
    }

    @RequestMapping(value = "/showBoards")
    public Object blogBoards(String account,int current,Model model ){
        Page<BlogBoardInfo> blogBoardInfoPages = blogBoardService .selectBlogBoardList(new Page<BlogBoardInfo>(current,5),account);
        model.addAttribute("blogBoardInfos",blogBoardInfoPages.getRecords());
        return "/blog/html/board.html#usertable";
    }

    /**
     * 跳转到添加博客留言
     */
    @RequestMapping("/blogBoard_add")
    public String blogBoardAdd() {
        return PREFIX + "blogBoard_add.html";
    }

    /**
     * 跳转到修改博客留言
     */
    @RequestMapping("/blogBoard_update/{blogBoardId}")
    public String blogBoardUpdate(@PathVariable Integer blogBoardId, Model model) {
        BlogBoard blogBoard = blogBoardService.selectById(blogBoardId);
        model.addAttribute("item",blogBoard);
        LogObjectHolder.me().set(blogBoard);
        return PREFIX + "blogBoard_edit.html";
    }

    /**
     * 获取博客留言列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<BlogBoard> page = new PageFactory<BlogBoard>().defaultPage();
        if (ToolUtil.isNotEmpty(condition)){
            blogBoardService.selectPage(page,new EntityWrapper<BlogBoard>().eq("account",ShiroKit.getUser().getAccount()).and().like("content",condition));
        }else{
            blogBoardService.selectPage(page,new EntityWrapper<BlogBoard>().eq("account",ShiroKit.getUser().getAccount()));
        }

        return super.packForBT(page);
    }

    /**
     * 新增博客留言
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BlogBoard blogBoard) {
        blogBoard.setCreatTime(new Date());
        blogBoardService.insert(blogBoard);
        return SUCCESS_TIP;
    }

    /**
     * 删除博客留言
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("ids[]") List<Integer> ids) {
        blogBoardService.deleteBatchIds(ids);;
        return SUCCESS_TIP;
    }

    /**
     * 修改博客留言
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BlogBoard blogBoard) {
        blogBoardService.updateById(blogBoard);
        return SUCCESS_TIP;
    }

    /**
     * 博客留言详情
     */
    @RequestMapping(value = "/detail/{blogBoardId}")
    @ResponseBody
    public Object detail(@PathVariable("blogBoardId") Integer blogBoardId) {
        return blogBoardService.selectById(blogBoardId);
    }


    /**
     * 博客留言导出
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export() throws IOException {
           Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("博客留言",""),
           BlogBoard.class,blogBoardService.selectList(null));
           HttpServletResponse response = HttpKit.getResponse();
           response.setContentType("application/vnd.ms-excel;charset=utf-8");
           String file_name = URLEncoder.encode("博客留言.xls","UTF-8");
           response.setHeader("Content-Disposition","attachment;filename="+file_name);
           ServletOutputStream out=response.getOutputStream();
           workbook.write(out);
           workbook.close();
           out.close();
    }

    /**
     * 博客留言导入
     */
    @RequestMapping(value = "/import")
    @ResponseBody
    public Object importExcel(@RequestPart("file") MultipartFile excel) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<BlogBoard> list = ExcelImportUtil.importExcel(excel.getInputStream(), BlogBoard.class, params);
        blogBoardService.insertBatch(list);
        return SUCCESS_TIP;
    }
}
