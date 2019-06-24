package com.stylefeng.gunSelf.modular.blog.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.base.tips.ErrorTip;
import com.stylefeng.gunSelf.core.shiro.ShiroKit;
import com.stylefeng.gunSelf.core.util.ToolUtil;
import com.stylefeng.gunSelf.modular.blog.vo.BlogCommentTopInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.CommentTop;
import com.stylefeng.gunSelf.modular.blog.service.ICommentTopService;
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
 * 评论点赞控制器
 *
 * @author fengshuonan
 * @Date 2019-02-26 14:40:37
 */
@Controller
@RequestMapping("/commentTop")
public class CommentTopController extends BaseController {

    private String PREFIX = "/blog/commentTop/";

    @Autowired
    private ICommentTopService commentTopService;

    /**
     * 跳转到评论点赞首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "commentTop.html";
    }

    /**
     * 跳转到添加评论点赞
     */
    @RequestMapping("/commentTop_add")
    public String commentTopAdd() {
        return PREFIX + "commentTop_add.html";
    }

    /**
     * 跳转到修改评论点赞
     */
    @RequestMapping("/commentTop_update/{commentTopId}")
    public String commentTopUpdate(@PathVariable Integer commentTopId, Model model) {
        CommentTop commentTop = commentTopService.selectById(commentTopId);
        model.addAttribute("item",commentTop);
        LogObjectHolder.me().set(commentTop);
        return PREFIX + "commentTop_edit.html";
    }

    /**
     * 获取评论点赞列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<BlogCommentTopInfo> page = new PageFactory<BlogCommentTopInfo>().defaultPage();
        commentTopService.selectCommentList(page,ShiroKit.getUser().getAccount(),condition);
        return super.packForBT(page);
    }

    /**
     * 新增评论点赞
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CommentTop commentTop) {
        if (ToolUtil.isEmpty(commentTop.getTpDate())){
            commentTop.setTpDate(new Date());
            if (ToolUtil.isNotEmpty(ShiroKit.getUser())){
                int tpPersonId = ShiroKit.getUser().id;
                int count = commentTopService.selectCount(new EntityWrapper<CommentTop>().
                        eq("commentId", commentTop.getCommentId()).
                        and().eq("tpPersonId", tpPersonId));
                if (count>0){
                    return new ErrorTip(400,"已经进行过点赞");
                }
                commentTop.setTpPersonId(tpPersonId);
            }else{
                return new ErrorTip(400,"请先登录");
            }
        }
        commentTopService.insert(commentTop);
        return SUCCESS_TIP;
    }

    /**
     * 删除评论点赞
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("ids[]") List<Integer> ids) {
        commentTopService.deleteBatchIds(ids);;
        return SUCCESS_TIP;
    }

    /**
     * 修改评论点赞
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CommentTop commentTop) {
        commentTopService.updateById(commentTop);
        return SUCCESS_TIP;
    }

    /**
     * 评论点赞详情
     */
    @RequestMapping(value = "/detail/{commentTopId}")
    @ResponseBody
    public Object detail(@PathVariable("commentTopId") Integer commentTopId) {
        return commentTopService.selectById(commentTopId);
    }


    /**
     * 评论点赞导出
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export() throws IOException {
           Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("评论点赞",""),
           CommentTop.class,commentTopService.selectList(null));
           HttpServletResponse response = HttpKit.getResponse();
           response.setContentType("application/vnd.ms-excel;charset=utf-8");
           String file_name = URLEncoder.encode("评论点赞.xls","UTF-8");
           response.setHeader("Content-Disposition","attachment;filename="+file_name);
           ServletOutputStream out=response.getOutputStream();
           workbook.write(out);
           workbook.close();
           out.close();
    }

    /**
     * 评论点赞导入
     */
    @RequestMapping(value = "/import")
    @ResponseBody
    public Object importExcel(@RequestPart("file") MultipartFile excel) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<CommentTop> list = ExcelImportUtil.importExcel(excel.getInputStream(), CommentTop.class, params);
        commentTopService.insertBatch(list);
        return SUCCESS_TIP;
    }
}
