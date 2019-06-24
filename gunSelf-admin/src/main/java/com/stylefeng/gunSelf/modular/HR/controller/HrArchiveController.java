package com.stylefeng.gunSelf.modular.HR.controller;

import com.stylefeng.gunSelf.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.HrArchive;
import com.stylefeng.gunSelf.modular.HR.service.IHrArchiveService;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2018-07-08 11:59:39
 */
@Controller
@RequestMapping("/hrArchive")
public class HrArchiveController extends BaseController {

    private String PREFIX = "/HR/hrArchive/";

    @Autowired
    private IHrArchiveService hrArchiveService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "hrArchive.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/hrArchive_add")
    public String hrArchiveAdd() {
        return PREFIX + "hrArchive_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/hrArchive_update/{hrArchiveId}")
    public String hrArchiveUpdate(@PathVariable Integer hrArchiveId, Model model) {
        HrArchive hrArchive = hrArchiveService.selectById(hrArchiveId);
        model.addAttribute("item",hrArchive);
        LogObjectHolder.me().set(hrArchive);
        return PREFIX + "hrArchive_edit.html";
    }

    /**
     * 获取列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<HrArchive> page = new PageFactory<HrArchive>().defaultPage();
        hrArchiveService.selectPage(page);
       // hrArchiveService.selectPage(page,new EntityWrapper<HrArchive>().eq("goods_name",condition));
        return super.packForBT(page);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(HrArchive hrArchive) {
        hrArchiveService.insert(hrArchive);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer hrArchiveId) {
        hrArchiveService.deleteById(hrArchiveId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(HrArchive hrArchive) {
        hrArchiveService.updateById(hrArchive);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{hrArchiveId}")
    @ResponseBody
    public Object detail(@PathVariable("hrArchiveId") Integer hrArchiveId) {
        return hrArchiveService.selectById(hrArchiveId);
    }
}
